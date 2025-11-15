package com.empresa.crud.cliente.controller;

import com.empresa.crud.cliente.service.ClienteService;
import com.empresa.crud.cliente.model.Cliente;
import com.empresa.crud.cliente.DTO.ClienteDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    //RF01
    @PostMapping
    public ResponseEntity<ClienteDTO> salvar(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.toEntity();
        Cliente clienteSalvo = clienteService.salvar(cliente);
        ClienteDTO dto = ClienteDTO.fromEntity(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    //RF02
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id,@Valid  @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteDTO.toEntity();
        Cliente clienteAtualizado = clienteService.atualizar(id, cliente);
        ClienteDTO dto = ClienteDTO.fromEntity(clienteAtualizado);
        return ResponseEntity.ok(dto);
    }
    //RF03
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    //RF04
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodos() {
        List<Cliente> clientes = clienteService.buscarTodos();
        List<ClienteDTO> clientesDTO = clientes.stream()
        .map(ClienteDTO::fromEntity)
        .toList();
        return ResponseEntity.ok(clientesDTO);
    }
    //RF05
    @GetMapping("/buscar")
    public ResponseEntity<List<ClienteDTO>> buscarPorNomeOuCpf(@RequestParam String termo) {
        List<Cliente> clientes = clienteService.buscarPorNomeOuCpf(termo);
        List<ClienteDTO> clientesDTO = clientes.stream()
        .map(ClienteDTO::fromEntity)
        .toList();  
        return ResponseEntity.ok(clientesDTO);
    }
}
