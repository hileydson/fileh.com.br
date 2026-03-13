package br.com.fileh.financial.controller;

import br.com.fileh.financial.model.FluxoCaixa;
import br.com.fileh.financial.repository.FluxoCaixaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fluxo-caixa")
public class FluxoCaixaController {

    @Autowired
    private FluxoCaixaRepository repository;

    @GetMapping("/entidade/{entidadeId}")
    public ResponseEntity<List<FluxoCaixa>> getAllByEntidade(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(repository.findByEntidadeId(entidadeId));
    }

    @PostMapping
    public ResponseEntity<FluxoCaixa> create(@RequestBody FluxoCaixa fluxo) {
        return ResponseEntity.ok(repository.save(fluxo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FluxoCaixa> update(@PathVariable Long id, @RequestBody FluxoCaixa details) {
        return repository.findById(id).map(fluxo -> {
            fluxo.setDescricao(details.getDescricao());
            fluxo.setEntidadeId(details.getEntidadeId());
            fluxo.setValor(details.getValor());
            fluxo.setDataCadastro(details.getDataCadastro());
            fluxo.setTipo(details.getTipo());
            fluxo.setFormaPagamento(details.getFormaPagamento());
            return ResponseEntity.ok(repository.save(fluxo));
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
