package com.empresa.crud.cliente.repository;

import com.empresa.crud.cliente.model.Contato;  

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

    List<Contato> findByClienteId(Long clienteId);
}
