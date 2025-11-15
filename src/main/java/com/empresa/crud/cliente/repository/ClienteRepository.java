package com.empresa.crud.cliente.repository;

import com.empresa.crud.cliente.model.Cliente;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    @Query("SELECT c FROM Cliente c WHERE " +
       "LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) OR " +
       "c.cpf LIKE CONCAT('%', :termo, '%')")
    List<Cliente> buscarPorNomeOuCpf(@Param("termo") String termo);

    Optional<Cliente> findByCpf(String cpf);
}
