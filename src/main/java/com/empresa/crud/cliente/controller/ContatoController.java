package com.empresa.crud.cliente.controller;

import com.empresa.crud.cliente.service.ContatoService;
import com.empresa.crud.cliente.model.Contato;
import com.empresa.crud.cliente.DTO.ContatoDTO;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/contatos")
public class ContatoController {
    @Autowired
    private ContatoService contatoService;

    @PostMapping
    public ResponseEntity<ContatoDTO> salvar(@Valid @RequestBody ContatoDTO contatoDTO) {
        Contato contato = contatoDTO.toEntity();
        Contato contatoSalvo = contatoService.salvar(contato);
        ContatoDTO dto = ContatoDTO.fromEntity(contatoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ContatoDTO> atualizar(@PathVariable Long id,@Valid @RequestBody ContatoDTO contatoDTO) {
        Contato contato = contatoDTO.toEntity();
        Contato contatoAtualizado = contatoService.atualizar(id, contato);
        ContatoDTO dto = ContatoDTO.fromEntity(contatoAtualizado);
        return ResponseEntity.ok(dto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        contatoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ContatoDTO>> buscarPorClienteId(@PathVariable Long clienteId) {
        List<Contato> contatos = contatoService.buscarPorClienteId(clienteId);
        List<ContatoDTO> contatosDTO = contatos.stream()    
        .map(ContatoDTO::fromEntity)
        .toList();
        return ResponseEntity.ok(contatosDTO);
    }
}