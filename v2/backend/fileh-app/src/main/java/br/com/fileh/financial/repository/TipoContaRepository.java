package br.com.fileh.financial.repository;

import br.com.fileh.financial.model.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, Long> {
    List<TipoConta> findByEntidadeId(Long entidadeId);
}
