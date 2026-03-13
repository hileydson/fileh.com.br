package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.SituacaoProposta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SituacaoPropostaRepository extends JpaRepository<SituacaoProposta, Long> {
}
