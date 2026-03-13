package br.com.fileh.financial.repository;

import br.com.fileh.financial.model.ContaPagar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContaPagarRepository extends JpaRepository<ContaPagar, Long> {
    List<ContaPagar> findByUsuarioId(Long usuarioId);
}
