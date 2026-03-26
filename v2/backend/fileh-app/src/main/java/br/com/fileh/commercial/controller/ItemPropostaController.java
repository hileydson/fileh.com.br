package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.ItemProposta;
import br.com.fileh.commercial.repository.ItemPropostaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itens-proposta")
public class ItemPropostaController {

    @Autowired
    private ItemPropostaRepository repository;

    @GetMapping("/proposta/{propostaId}")
    public ResponseEntity<List<ItemProposta>> getAllByProposta(@PathVariable Long propostaId) {
        return ResponseEntity.ok(repository.findByPropostaId(propostaId));
    }

    @PostMapping
    public ResponseEntity<ItemProposta> create(@RequestBody ItemProposta item) {
        return ResponseEntity.ok(repository.save(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemProposta> update(@PathVariable Long id, @RequestBody ItemProposta details) {
        return repository.findById(id).map(item -> {
            item.setDescricao(details.getDescricao());
            item.setValor(details.getValor());
            item.setQuantidade(details.getQuantidade());
            item.setValorDesconto(details.getValorDesconto());
            item.setUnidade(details.getUnidade());
            return ResponseEntity.ok(repository.save(item));
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
