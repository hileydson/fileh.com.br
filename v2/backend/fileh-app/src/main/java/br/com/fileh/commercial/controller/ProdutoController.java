package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.Produto;
import br.com.fileh.commercial.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<Produto>> getAllByTenant(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Produto>> search(@RequestParam Long entidadeId, @RequestParam String query) {
        String[] words = query.split("\\s+");
        org.springframework.data.jpa.domain.Specification<Produto> spec = (root, q, cb) -> cb.conjunction();
        for (String word : words) {
            if (word.isEmpty()) continue;
            final String pattern = "%" + word.toLowerCase() + "%";
            org.springframework.data.jpa.domain.Specification<Produto> wordSpec = (root, q, cb) ->
                cb.or(cb.like(cb.lower(root.get("descricao")), pattern), cb.like(cb.lower(root.get("sku")), pattern));
            spec = spec.and(wordSpec);
        }
        return ResponseEntity.ok(repository.findAll(spec));
    }

    @PostMapping
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        return ResponseEntity.ok(repository.save(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto details) {
        return repository.findById(id).map(produto -> {
            produto.setDescricao(details.getDescricao());
            produto.setSku(details.getSku());
            produto.setValorVenda(details.getValorVenda());
            produto.setUnidade(details.getUnidade());
            produto.setEstoque(details.getEstoque());
            return ResponseEntity.ok(repository.save(produto));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/all/{entidadeId}")
    public ResponseEntity<Void> deleteAll(@PathVariable Long entidadeId) {
        List<Produto> existing = repository.findByEntidadeId(entidadeId);
        repository.deleteAll(existing);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/import/{entidadeId}")
    public ResponseEntity<Integer> importAdd(@PathVariable Long entidadeId, @RequestBody List<Produto> produtos) {
        for (Produto p : produtos) {
            p.setEntidadeId(entidadeId);
            p.setId(null);
            repository.save(p);
        }
        return ResponseEntity.ok(produtos.size());
    }

    @PostMapping("/import-replace/{entidadeId}")
    public ResponseEntity<Integer> importReplace(@PathVariable Long entidadeId, @RequestBody List<Produto> produtos) {
        List<Produto> existing = repository.findByEntidadeId(entidadeId);
        repository.deleteAll(existing);
        for (Produto p : produtos) {
            p.setEntidadeId(entidadeId);
            p.setId(null);
            repository.save(p);
        }
        return ResponseEntity.ok(produtos.size());
    }
}
