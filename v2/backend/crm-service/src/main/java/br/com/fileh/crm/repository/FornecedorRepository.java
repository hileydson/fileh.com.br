package br.com.fileh.crm.repository;

import br.com.fileh.crm.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long>, JpaSpecificationExecutor<Fornecedor> {
    List<Fornecedor> findByEntidadeId(Long entidadeId);
    List<Fornecedor> findByEntidadeIdAndNomeContainingIgnoreCase(Long entidadeId, String nome);
}
