package br.com.fileh.crm.controller;

import br.com.fileh.crm.model.Entidade;
import br.com.fileh.crm.repository.EntidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidades")
public class EntidadeController {

    @Autowired
    private EntidadeRepository entidadeRepository;

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Entidade>> getAllEntidadesByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(entidadeRepository.findByUsuarioId(tenantId));
    }

    @GetMapping("/global")
    public ResponseEntity<List<Entidade>> getAllGlobal() {
        return ResponseEntity.ok(entidadeRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Entidade>> searchEntidades(@RequestParam Long tenantId, @RequestParam String query) {
        String[] words = query.split("\\s+");
        org.springframework.data.jpa.domain.Specification<Entidade> spec = (root, q, cb) -> 
            cb.equal(root.get("usuarioId"), tenantId);

        for (String word : words) {
            if (word.isEmpty()) continue;
            final String pattern = "%" + word.toLowerCase() + "%";
            org.springframework.data.jpa.domain.Specification<Entidade> wordSpec = (root, q, cb) -> 
                cb.like(cb.lower(root.get("nome")), pattern);
            spec = spec.and(wordSpec);
        }

        return ResponseEntity.ok(entidadeRepository.findAll(spec));
    }

    @PostMapping
    public ResponseEntity<Entidade> createEntidade(@RequestBody Entidade entidade) {
        return ResponseEntity.ok(entidadeRepository.save(entidade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entidade> updateEntidade(@PathVariable Long id, @RequestBody Entidade entidadeDetails) {
        return entidadeRepository.findById(id).map(entidade -> {
            entidade.setNome(entidadeDetails.getNome());
            return ResponseEntity.ok(entidadeRepository.save(entidade));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntidade(@PathVariable Long id) {
        if (entidadeRepository.existsById(id)) {
            entidadeRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
