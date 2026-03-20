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
        if (conta.getTotalParcelas() != null && conta.getTotalParcelas() > 1) {
            ContaReceber firstInstallment = null;
            for (int i = 1; i <= conta.getTotalParcelas(); i++) {
                ContaReceber parcela = new ContaReceber();
                parcela.setDescricao(conta.getDescricao());
                parcela.setEntidadeId(conta.getEntidadeId());
                parcela.setValor(conta.getValor());
                parcela.setDataCadastro(conta.getDataCadastro());
                parcela.setNumeroDocumento(conta.getNumeroDocumento());
                parcela.setFornecedor(conta.getFornecedor());
                parcela.setTipoContaId(conta.getTipoContaId());
                parcela.setRecebido(false);
                parcela.setParcelado(true);
                parcela.setNumeroParcela(i);
                
                // Increment month for each installment
                java.time.LocalDate dueDate = conta.getDataVencimento() != null ? conta.getDataVencimento().plusMonths(i - 1) : java.time.LocalDate.now().plusMonths(i - 1);
                parcela.setDataVencimento(dueDate);
                
                ContaReceber saved = repository.save(parcela);
                if (i == 1) firstInstallment = saved;
            }
            return ResponseEntity.ok(firstInstallment);
        }
        return ResponseEntity.ok(repository.save(conta));
    }

    @PutMapping("/bulk-recebido")
    public ResponseEntity<Void> bulkUpdateStatus(@RequestBody List<Long> ids, @RequestParam Boolean recebido) {
        List<ContaReceber> contas = repository.findAllById(ids);
        contas.forEach(c -> c.setRecebido(recebido));
        repository.saveAll(contas);
        return ResponseEntity.ok().build();
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
