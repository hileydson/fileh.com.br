package br.com.fileh.financial.repository;

import br.com.fileh.financial.model.ContaReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContaReceberRepository extends JpaRepository<ContaReceber, Long> {
    List<ContaReceber> findByUsuarioId(Long usuarioId);
}
