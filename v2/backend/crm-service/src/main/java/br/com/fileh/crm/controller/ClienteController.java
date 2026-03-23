package br.com.fileh.crm.controller;

import br.com.fileh.crm.model.Cliente;
import br.com.fileh.crm.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/tenant/{entidadeId}")
    public ResponseEntity<List<Cliente>> getAllClientesByTenant(@PathVariable Long entidadeId) {
        // Now global: returning all customers regardless of entidadeId
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Cliente>> searchClientes(@RequestParam(required = false) Long entidadeId, @RequestParam String query) {
        String[] words = query.split("\\s+");
        // Start with a specification that is always true, or starts with entidadeId if provided
        org.springframework.data.jpa.domain.Specification<Cliente> spec = (root, q, cb) -> cb.conjunction();
        
        if (entidadeId != null && entidadeId > 0) {
            spec = spec.and((root, q, cb) -> cb.equal(root.get("entidadeId"), entidadeId));
        }

        for (String word : words) {
            if (word.isEmpty()) continue;
            final String pattern = "%" + word.toLowerCase() + "%";
            
            org.springframework.data.jpa.domain.Specification<Cliente> wordSpec = (root, q, cb) -> {
                try {
                    Long id = Long.parseLong(word);
                    return cb.or(
                        cb.equal(root.get("id"), id),
                        cb.like(cb.lower(root.get("nome")), pattern),
                        cb.like(cb.lower(root.get("cpf")), pattern)
                    );
                } catch (NumberFormatException e) {
                    return cb.or(
                        cb.like(cb.lower(root.get("nome")), pattern),
                        cb.like(cb.lower(root.get("cpf")), pattern)
                    );
                }
            };
            spec = spec.and(wordSpec);
        }

        return ResponseEntity.ok(clienteRepository.findAll(spec));
    }

    @PostMapping
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteRepository.save(cliente));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        return clienteRepository.findById(id).map(cliente -> {
            cliente.setNome(clienteDetails.getNome());
            cliente.setCpf(clienteDetails.getCpf());
            cliente.setTelefone(clienteDetails.getTelefone());
            cliente.setBairro(clienteDetails.getBairro());
            cliente.setLogradouro(clienteDetails.getLogradouro());
            cliente.setUf(clienteDetails.getUf());
            return ResponseEntity.ok(clienteRepository.save(cliente));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
