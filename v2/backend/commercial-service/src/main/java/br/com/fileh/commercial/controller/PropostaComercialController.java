package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.PropostaComercial;
import br.com.fileh.commercial.repository.PropostaComercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/propostas")
public class PropostaComercialController {

    @Autowired
    private PropostaComercialRepository repository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<PropostaComercial>> getAllByTenant(
            @PathVariable Long entidadeId,
            @RequestParam(required = false, defaultValue = "true") Boolean ativo) {
        return ResponseEntity.ok(repository.findByEntidadeIdAndAtivo(entidadeId, ativo));
    }

    @GetMapping("/global")
    public ResponseEntity<List<PropostaComercial>> getAllGlobal() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<PropostaComercial> create(@RequestBody PropostaComercial proposta) {
        return ResponseEntity.ok(repository.save(proposta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PropostaComercial> update(@PathVariable Long id, @RequestBody PropostaComercial details) {
        return repository.findById(id).map(proposta -> {
            proposta.setEntidadeId(details.getEntidadeId());
            proposta.setUsuarioId(details.getUsuarioId());
            proposta.setClienteId(details.getClienteId());
            proposta.setValorDesconto(details.getValorDesconto());
            proposta.setValorFrete(details.getValorFrete());
            proposta.setValorTotal(details.getValorTotal());
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
        return repository.findById(id).map(proposta -> {
            proposta.setAtivo(false);
            repository.save(proposta);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
