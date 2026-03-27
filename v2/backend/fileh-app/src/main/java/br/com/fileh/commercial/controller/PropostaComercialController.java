package br.com.fileh.commercial.controller;

import br.com.fileh.commercial.model.PropostaComercial;
import br.com.fileh.commercial.repository.PropostaComercialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;

@RestController
@RequestMapping("/api/propostas")
public class PropostaComercialController {

    @Autowired
    private PropostaComercialRepository repository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<?> getAllByTenant(@PathVariable Long entidadeId,
            @RequestParam(required = false, defaultValue = "true") Boolean ativo) {
        try {
            return ResponseEntity.ok(repository.findListByEntidadeIdAndAtivo(entidadeId, ativo));
        } catch (Exception e) {
            logError(e, "getAllByTenant");
            return ResponseEntity.status(500).body("Error retrieving proposals: " + e.getMessage());
        }
    }

    @GetMapping("/global")
    public ResponseEntity<?> getAllGlobal() {
        try {
            return ResponseEntity.ok(repository.findAllList());
        } catch (Exception e) {
            logError(e, "getAllGlobal");
            return ResponseEntity.status(500).body("Error retrieving global proposals: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody PropostaComercial proposta) {
        try {
            PropostaComercial saved = repository.save(proposta);
            return ResponseEntity.ok(repository.findById(saved.getId()).orElse(saved));
        } catch (Exception e) {
            logError(e, "create");
            return ResponseEntity.status(500).body("Error saving proposal: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody PropostaComercial details) {
        try {
            return repository.findById(id).map(proposta -> {
                proposta.setEntidadeId(details.getEntidadeId());
                proposta.setUsuarioId(details.getUsuarioId());
                proposta.setClienteId(details.getClienteId());
                proposta.setValorDesconto(details.getValorDesconto());
                proposta.setDataCadastro(details.getDataCadastro());
                proposta.setDataPrevista(details.getDataPrevista());
                proposta.setObservacao(details.getObservacao());
                proposta.setAtendente(details.getAtendente());
                proposta.setSituacao(details.getSituacao());
                proposta.setFormaPagamento(details.getFormaPagamento());
                proposta.setAtivo(details.getAtivo() != null ? details.getAtivo() : proposta.getAtivo());
                repository.save(proposta);
                return ResponseEntity.ok("{\"id\": " + proposta.getId() + "}");
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logError(e, "update");
            return ResponseEntity.status(500).body("Error updating proposal: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return repository.findById(id).map(proposta -> {
                proposta.setAtivo(false);
                repository.save(proposta);
                return ResponseEntity.ok().<Void>build();
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logError(e, "delete");
            return ResponseEntity.status(500).body("Error deleting proposal: " + e.getMessage());
        }
    }

    private void logError(Exception e, String method) {
        try (java.io.PrintWriter pw = new java.io.PrintWriter(new FileWriter("/tmp/h_error.txt", true))) {
            pw.println("--- Error in " + method + " at " + java.time.LocalDateTime.now() + " ---");
            e.printStackTrace(pw);
            pw.println("------------------------------------------");
        } catch (Exception logEx) {}
    }
}
