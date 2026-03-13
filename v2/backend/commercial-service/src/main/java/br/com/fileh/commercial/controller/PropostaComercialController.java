package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.PropostaComercial;
import br.com.fileh.commercial.repository.PropostaComercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/propostas")
public class PropostaComercialController {

    @Autowired
    private PropostaComercialRepository repository;

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<PropostaComercial>> getAllByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(repository.findByUsuarioId(tenantId));
    }

    @PostMapping
    public ResponseEntity<PropostaComercial> create(@RequestBody PropostaComercial proposta) {
        return ResponseEntity.ok(repository.save(proposta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropostaComercial> update(@PathVariable Long id, @RequestBody PropostaComercial details) {
        return repository.findById(id).map(proposta -> {
            proposta.setClienteId(details.getClienteId());
            proposta.setValorDesconto(details.getValorDesconto());
            proposta.setValorFrete(details.getValorFrete());
            proposta.setDataCadastro(details.getDataCadastro());
            proposta.setDataPrevista(details.getDataPrevista());
            proposta.setObservacao(details.getObservacao());
            proposta.setAtendente(details.getAtendente());
            proposta.setSituacao(details.getSituacao());
            proposta.setFormaPagamento(details.getFormaPagamento());
            return ResponseEntity.ok(repository.save(proposta));
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
