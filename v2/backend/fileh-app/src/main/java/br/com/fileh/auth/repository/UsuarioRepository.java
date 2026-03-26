package br.com.fileh.auth.repository;

import br.com.fileh.auth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findBySubdominio(String subdominio);
    Optional<Usuario> findByCnpj(String cnpj);
}
