package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {
    List<FormaPagamento> findByEntidadeId(Long entidadeId);
    List<FormaPagamento> findByEntidadeIdAndTipo(Long entidadeId, String tipo);
    List<FormaPagamento> findByTipo(String tipo);
}
