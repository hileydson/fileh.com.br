package br.com.fileh.financial.controller;

import br.com.fileh.financial.model.ContaPagar;
import br.com.fileh.financial.repository.ContaPagarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas-pagar")
public class ContaPagarController {

    @Autowired
    private ContaPagarRepository repository;

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<ContaPagar>> getAllByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(repository.findByUsuarioId(tenantId));
    }

    @PostMapping
    public ResponseEntity<ContaPagar> create(@RequestBody ContaPagar conta) {
        return ResponseEntity.ok(repository.save(conta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaPagar> update(@PathVariable Long id, @RequestBody ContaPagar details) {
        return repository.findById(id).map(conta -> {
            conta.setDescricao(details.getDescricao());
            conta.setDataVencimento(details.getDataVencimento());
            conta.setValor(details.getValor());
            conta.setDataCadastro(details.getDataCadastro());
            conta.setNumeroDocumento(details.getNumeroDocumento());
            conta.setNumeroParcela(details.getNumeroParcela());
            conta.setFornecedor(details.getFornecedor());
            conta.setPago(details.getPago());
            conta.setTipoConta(details.getTipoConta());
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
