package br.com.fileh.auth.controller;

import br.com.fileh.crm.model.Entidade;
import br.com.fileh.crm.repository.EntidadeRepository;
import br.com.fileh.auth.payload.request.LoginRequest;
import br.com.fileh.auth.payload.response.JwtResponse;
import br.com.fileh.auth.security.jwt.JwtUtils;
import br.com.fileh.auth.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    EntidadeRepository entidadeRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(), userDetails.getTenantId(), userDetails.getEntidadeId(),
                userDetails.getUsername(), userDetails.getName(), userDetails.getEmail(),
                roles, userDetails.getMsgFooter(), userDetails.isDefaultPassword(),
                userDetails.getEntidadeNome()));
    }

    @PostMapping("/switch-entidade/{entidadeId}")
    public ResponseEntity<?> switchEntidade(@PathVariable Long entidadeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        // Manual role check (Admins only)
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            return ResponseEntity.status(403).body("Apenas administradores podem trocar de entidade.");
        }

        return entidadeRepository.findById(entidadeId).map(entidade -> {
            if (!entidade.getUsuarioId().equals(userDetails.getTenantId())) {
                return ResponseEntity.status(403).body("Entidade não pertence ao seu tenant.");
            }

            String jwt = jwtUtils.generateJwtTokenForEntidade(authentication, entidadeId, entidade.getNome());
            
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getId(), userDetails.getTenantId(), entidadeId,
                    userDetails.getUsername(), userDetails.getName(), userDetails.getEmail(),
                    roles, userDetails.getMsgFooter(), userDetails.isDefaultPassword(),
                    entidade.getNome()));
        }).orElse(ResponseEntity.notFound().build());
    }
}
