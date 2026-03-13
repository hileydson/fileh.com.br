package br.com.fileh.crm.repository;

import br.com.fileh.crm.model.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntidadeRepository extends JpaRepository<Entidade, Long> {
    List<Entidade> findByUsuarioId(Long usuarioId);
}
