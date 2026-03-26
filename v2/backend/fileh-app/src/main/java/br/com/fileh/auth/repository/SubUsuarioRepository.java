package br.com.fileh.auth.repository;

import br.com.fileh.auth.model.SubUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface SubUsuarioRepository extends JpaRepository<SubUsuario, Long> {
    Optional<SubUsuario> findByLogin(String login);
    List<SubUsuario> findByUsuarioId(Long usuarioId);

    @Query(value = "SELECT ent_nm_entidade FROM entidade WHERE ent_cd_entidade = :id", nativeQuery = true)
    String findEntidadeNome(@Param("id") Long id);

    @Query(value = "SELECT usu_nm_usuario FROM usuario WHERE usu_cd_usuario = :id", nativeQuery = true)
    String findUsuarioNome(@Param("id") Long id);
}
