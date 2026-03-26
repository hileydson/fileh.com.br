package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.SituacaoProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SituacaoPropostaRepository extends JpaRepository<SituacaoProposta, Long> {
    List<SituacaoProposta> findByEntidadeId(Long entidadeId);
}
