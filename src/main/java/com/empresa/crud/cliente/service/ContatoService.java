package com.empresa.crud.cliente.service;

import com.empresa.crud.cliente.model.Contato;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;  
import com.empresa.crud.cliente.repository.ContatoRepository;

import java.util.List;

@Service
public class ContatoService {
    @Autowired
    private ContatoRepository contatoRepository;

    @Autowired
    private ClienteService clienteService;

    private Contato buscarContatoPorId(Long id){
        return contatoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }
    
    //RF06
    public Contato salvar(Contato contato) {
        clienteService.validarClienteNulo(contato.getCliente());
        clienteService.buscarClientePorId(contato.getCliente().getId());
        return contatoRepository.save(contato);
    }
    //RF07                        
    public Contato atualizar(Long id, Contato contato) {
        clienteService.validarClienteNulo(contato.getCliente());
        clienteService.buscarClientePorId(contato.getCliente().getId());
        Contato contatoExistente = buscarContatoPorId(id);
        contatoExistente.setTipoContato(contato.getTipoContato());
        contatoExistente.setValorContato(contato.getValorContato());
        contatoExistente.setObservacao(contato.getObservacao());
        contatoExistente.setCliente(contato.getCliente());
        return contatoRepository.save(contatoExistente);
    }

    //RF08
    public void deletar(Long id) {
        Contato contato = buscarContatoPorId(id);
        contatoRepository.delete(contato);
    }
    //RF09
    public List<Contato> buscarPorClienteId(Long clienteId) {
        return contatoRepository.findByClienteId(clienteId);
    }
}
