package br.com.fileh.auth.controller;

import br.com.fileh.auth.model.SubUsuario;
import br.com.fileh.auth.repository.SubUsuarioRepository;
import br.com.fileh.auth.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/subusuarios")
@Transactional
public class SubUsuarioController {

    @Autowired
    private SubUsuarioRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<SubUsuario>> getAllByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(repository.findByUsuarioId(tenantId));
    }

    @PostMapping("/{tenantId}")
    public ResponseEntity<SubUsuario> create(@PathVariable Long tenantId, @RequestBody SubUsuario subUsuario) {
        return usuarioRepository.findById(tenantId).map(usuario -> {
            subUsuario.setUsuario(usuario);
            // Default password if not provided
            if (subUsuario.getSenha() == null || subUsuario.getSenha().trim().isEmpty()) {
                subUsuario.setSenha("1234");
            }
            return ResponseEntity.ok(repository.save(subUsuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubUsuario> update(@PathVariable Long id, @RequestBody SubUsuario details) {
        return repository.findById(id).map(subUsuario -> {
            subUsuario.setNome(details.getNome());
            subUsuario.setLogin(details.getLogin());
            if (details.getSenha() != null && !details.getSenha().isEmpty()) {
                subUsuario.setSenha(details.getSenha()); // TODO: Add encoder if needed
            }
            subUsuario.setEntidadeId(details.getEntidadeId());
            subUsuario.setIsAdm(details.getIsAdm());
            subUsuario.setModuloFinanceiro(details.getModuloFinanceiro());
            subUsuario.setModuloVenda(details.getModuloVenda());
            subUsuario.setModuloCliente(details.getModuloCliente());
            subUsuario.setModuloCaixa(details.getModuloCaixa());
            subUsuario.setMsgFooter(details.getMsgFooter());
            return ResponseEntity.ok(repository.save(subUsuario));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/profile/{id}")
    public ResponseEntity<SubUsuario> patchProfile(@PathVariable Long id, @RequestBody SubUsuario details) {
        return repository.findById(id).map(subUsuario -> {
            if (details.getMsgFooter() != null) {
                subUsuario.setMsgFooter(details.getMsgFooter());
            }
            if (details.getSenha() != null && !details.getSenha().trim().isEmpty()) {
                subUsuario.setSenha(details.getSenha());
            }
            return ResponseEntity.ok(repository.save(subUsuario));
        }).orElse(ResponseEntity.notFound().build());
    }
}
