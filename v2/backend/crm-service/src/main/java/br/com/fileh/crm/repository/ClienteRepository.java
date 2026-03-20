package br.com.fileh.crm.repository;

import br.com.fileh.crm.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    List<Cliente> findByEntidadeId(Long entidadeId);
}
