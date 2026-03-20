package br.com.fileh.financial.controller;

import br.com.fileh.financial.model.TipoConta;
import br.com.fileh.financial.repository.TipoContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financial/tipo-conta")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TipoContaController {

    @Autowired
    private TipoContaRepository repository;

    @GetMapping("/entidade/{entidadeId}")
    public List<TipoConta> listarPorEntidade(@PathVariable Long entidadeId) {
        return repository.findByEntidadeId(entidadeId);
    }

    @PostMapping
    public TipoConta salvar(@RequestBody TipoConta tipoConta) {
        return repository.save(tipoConta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoConta> atualizar(@PathVariable Long id, @RequestBody TipoConta tipoContaDetails) {
        return repository.findById(id)
                .map(tipoConta -> {
                    tipoConta.setNome(tipoContaDetails.getNome());
                    tipoConta.setEntidadeId(tipoContaDetails.getEntidadeId());
                    return ResponseEntity.ok(repository.save(tipoConta));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        return repository.findById(id)
                .map(tipoConta -> {
                    repository.delete(tipoConta);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoConta> buscarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
