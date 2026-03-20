package br.com.fileh.auth.security.services;

import br.com.fileh.auth.model.SubUsuario;
import br.com.fileh.auth.repository.SubUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    SubUsuarioRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SubUsuario user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        String entidadeNome = null;
        if (user.getEntidadeId() != null) {
            entidadeNome = userRepository.findEntidadeNome(user.getEntidadeId());
        } else if (user.getUsuario() != null) {
            entidadeNome = userRepository.findUsuarioNome(user.getUsuario().getId());
        }

        return UserDetailsImpl.build(user, entidadeNome);
    }
}
