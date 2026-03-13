package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.ItemProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemPropostaRepository extends JpaRepository<ItemProposta, Long> {
    List<ItemProposta> findByPropostaId(Long propostaId);
}
