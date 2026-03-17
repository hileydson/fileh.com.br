package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.SituacaoProposta;
import br.com.fileh.commercial.repository.SituacaoPropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/situacoes-proposta")
public class SituacaoPropostaController {

    @Autowired
    private SituacaoPropostaRepository repository;

    @GetMapping
    public ResponseEntity<List<SituacaoProposta>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<SituacaoProposta>> getAllByTenant(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(repository.findByEntidadeId(entidadeId));
    }

    @PostMapping
    public ResponseEntity<SituacaoProposta> create(@RequestBody SituacaoProposta situacao) {
        return ResponseEntity.ok(repository.save(situacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SituacaoProposta> update(@PathVariable Long id, @RequestBody SituacaoProposta details) {
        return repository.findById(id).map(situacao -> {
            situacao.setDescricao(details.getDescricao());
            return ResponseEntity.ok(repository.save(situacao));
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
}
