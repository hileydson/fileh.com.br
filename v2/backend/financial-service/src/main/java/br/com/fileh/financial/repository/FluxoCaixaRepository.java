package br.com.fileh.financial.repository;

import br.com.fileh.financial.model.FluxoCaixa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface FluxoCaixaRepository extends JpaRepository<FluxoCaixa, Long> {
    List<FluxoCaixa> findByEntidadeId(Long entidadeId);
    List<FluxoCaixa> findByEntidadeIdAndDataCadastro(Long entidadeId, LocalDate dataCadastro);
}
