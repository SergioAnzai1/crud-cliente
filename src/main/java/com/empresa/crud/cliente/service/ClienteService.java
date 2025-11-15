package com.empresa.crud.cliente.service;

import com.empresa.crud.cliente.model.Cliente;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.empresa.crud.cliente.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public void validarClienteNulo(Cliente cliente){
        if (cliente == null || cliente.getId() == null){
            throw new RuntimeException("Cliente é obrigatório");
        }
    }
    public Cliente buscarClientePorId(Long id){
        return clienteRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }
    //RN03 CPF Único
    private void validarCpfUnico(String cpf, Long idExcluir){
        Optional<Cliente> clienteComCpf = clienteRepository.findByCpf(cpf);
        if (clienteComCpf.isPresent()) {
            if (idExcluir == null || !clienteComCpf.get().getId().equals(idExcluir)) {
                throw new RuntimeException("CPF já cadastrado");
            }
        }
    }
        
    
    //RF01
    public Cliente salvar(Cliente cliente) {
        if (cliente == null){
            throw new RuntimeException("Cliente é obrigatório");
        }
        validarCpfUnico(cliente.getCpf(), null);
        return clienteRepository.save(cliente);
    }
    //RF02
    public Cliente atualizar(Long id, Cliente cliente) {
        validarClienteNulo(cliente);
        Cliente clienteExistente = buscarClientePorId(id);
        validarCpfUnico(cliente.getCpf(), id);
        clienteExistente.setNome(cliente.getNome());
        clienteExistente.setCpf(cliente.getCpf());
        clienteExistente.setDataNascimento(cliente.getDataNascimento());
        clienteExistente.setEndereco(cliente.getEndereco());
        return clienteRepository.save(clienteExistente);
    }
    //RF03
    public void deletar(Long id) {
        buscarClientePorId(id);
        clienteRepository.deleteById(id);
    }
    //RF04
    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }
    //RF05
    public List<Cliente> buscarPorNomeOuCpf(String termo) {
        return clienteRepository.buscarPorNomeOuCpf(termo);
    }
}
