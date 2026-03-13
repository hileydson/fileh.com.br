package br.com.fileh.auth.repository;

import br.com.fileh.auth.model.SubUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubUsuarioRepository extends JpaRepository<SubUsuario, Long> {
    Optional<SubUsuario> findByLogin(String login);
}
