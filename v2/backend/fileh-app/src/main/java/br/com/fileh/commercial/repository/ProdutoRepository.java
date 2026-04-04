package br.com.fileh.commercial.repository;

import br.com.fileh.commercial.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {
    List<Produto> findByEntidadeId(Long entidadeId);
    List<Produto> findByEntidadeIdOrderByDescricaoAsc(Long entidadeId);
    List<Produto> findByEntidadeIdAndDescricaoContainingIgnoreCaseOrEntidadeIdAndSkuContainingIgnoreCase(Long eid1, String desc, Long eid2, String sku);
}
