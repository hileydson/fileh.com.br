package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.FormaPagamento;
import br.com.fileh.commercial.repository.FormaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository repository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<FormaPagamento>> getAllByTenant(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(repository.findByEntidadeId(entidadeId));
    }

    @PostMapping
    public ResponseEntity<FormaPagamento> create(@RequestBody FormaPagamento formaPagamento) {
        return ResponseEntity.ok(repository.save(formaPagamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamento> update(@PathVariable Long id, @RequestBody FormaPagamento details) {
        return repository.findById(id).map(formaPagamento -> {
            formaPagamento.setDescricao(details.getDescricao());
            return ResponseEntity.ok(repository.save(formaPagamento));
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
