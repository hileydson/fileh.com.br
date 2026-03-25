package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.PropostaComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropostaComercialRepository extends JpaRepository<PropostaComercial, Long> {
    List<PropostaComercial> findByEntidadeId(Long entidadeId);
    List<PropostaComercial> findByEntidadeIdAndAtivo(Long entidadeId, Boolean ativo);
}
