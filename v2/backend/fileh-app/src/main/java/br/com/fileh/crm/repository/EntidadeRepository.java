package br.com.fileh.crm.repository;

import br.com.fileh.crm.model.Entidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface EntidadeRepository extends JpaRepository<Entidade, Long>, JpaSpecificationExecutor<Entidade> {
    List<Entidade> findByUsuarioId(Long usuarioId);
    List<Entidade> findByUsuarioIdAndNomeContainingIgnoreCase(Long usuarioId, String nome);
}
