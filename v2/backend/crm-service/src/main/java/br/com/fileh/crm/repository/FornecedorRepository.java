package br.com.fileh.crm.repository;

import br.com.fileh.crm.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    List<Fornecedor> findByUsuarioId(Long usuarioId);
}
