package com.empresa.crud.cliente.repository;

import com.empresa.crud.cliente.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    List<Cliente> findByNomeContainingIgnoreCaseOrCpfContainingIgnoreCase(String nome, String cpf);
}
