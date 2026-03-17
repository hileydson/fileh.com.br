package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByEntidadeId(Long entidadeId);
}
