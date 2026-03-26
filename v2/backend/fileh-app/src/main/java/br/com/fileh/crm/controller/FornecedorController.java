package br.com.fileh.crm.controller;

import br.com.fileh.crm.model.Fornecedor;
import br.com.fileh.crm.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<Fornecedor>> getAllFornecedoresByTenant(@PathVariable Long entidadeId) {
        return ResponseEntity.ok(fornecedorRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Fornecedor>> searchFornecedores(@RequestParam Long entidadeId, @RequestParam String query) {
        String[] words = query.split("\\s+");
        org.springframework.data.jpa.domain.Specification<Fornecedor> spec = (root, q, cb) -> cb.conjunction();
        for (String word : words) {
            if (word.isEmpty()) continue;
            final String pattern = "%" + word.toLowerCase() + "%";
            org.springframework.data.jpa.domain.Specification<Fornecedor> wordSpec = (root, q, cb) ->
                cb.or(cb.like(cb.lower(root.get("nome")), pattern), cb.like(cb.lower(root.get("cnpj")), pattern));
            spec = spec.and(wordSpec);
        }
        return ResponseEntity.ok(fornecedorRepository.findAll(spec));
    }

    @PostMapping
    public ResponseEntity<Fornecedor> createFornecedor(@RequestBody Fornecedor fornecedor) {
        return ResponseEntity.ok(fornecedorRepository.save(fornecedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> updateFornecedor(@PathVariable Long id, @RequestBody Fornecedor fornecedorDetails) {
        return fornecedorRepository.findById(id).map(fornecedor -> {
            fornecedor.setNome(fornecedorDetails.getNome());
            fornecedor.setCnpj(fornecedorDetails.getCnpj());
            fornecedor.setLogradouro(fornecedorDetails.getLogradouro());
            fornecedor.setBairro(fornecedorDetails.getBairro());
            fornecedor.setCidade(fornecedorDetails.getCidade());
            fornecedor.setCep(fornecedorDetails.getCep());
            fornecedor.setInscricaoEstadual(fornecedorDetails.getInscricaoEstadual());
            fornecedor.setInscricaoMunicipal(fornecedorDetails.getInscricaoMunicipal());
            fornecedor.setContato(fornecedorDetails.getContato());
            return ResponseEntity.ok(fornecedorRepository.save(fornecedor));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFornecedor(@PathVariable Long id) {
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
