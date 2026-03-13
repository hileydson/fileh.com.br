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

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Cliente>> getAllClientesByTenant(@PathVariable Long tenantId) {
        return ResponseEntity.ok(clienteRepository.findByUsuarioId(tenantId));
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
