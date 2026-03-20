package br.com.fileh.financial.controller;

import br.com.fileh.financial.model.ContaReceber;
import br.com.fileh.financial.repository.ContaReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas-receber")
public class ContaReceberController {

    @Autowired
    private ContaReceberRepository repository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<ContaReceber>> getAllByTenant(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(repository.findByEntidadeId(entidadeId));
    }

    @PostMapping
    public ResponseEntity<ContaReceber> create(@RequestBody ContaReceber conta) {
        return ResponseEntity.ok(repository.save(conta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaReceber> update(@PathVariable Long id, @RequestBody ContaReceber details) {
        return repository.findById(id).map(conta -> {
            conta.setDescricao(details.getDescricao());
            conta.setDataVencimento(details.getDataVencimento());
            conta.setValor(details.getValor());
            conta.setDataCadastro(details.getDataCadastro());
            conta.setNumeroDocumento(details.getNumeroDocumento());
            conta.setNumeroParcela(details.getNumeroParcela());
            conta.setFornecedor(details.getFornecedor());
            conta.setRecebido(details.getRecebido());
            conta.setTipoContaId(details.getTipoContaId());
            return ResponseEntity.ok(repository.save(conta));
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
