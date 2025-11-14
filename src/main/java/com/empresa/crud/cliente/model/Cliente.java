package com.empresa.crud.cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Nome é obrigatório") //RN01
    @Column(nullable = false) //Protejer o banco de dados de entrada nulas //RN04
    private String nome;
    @NotBlank(message = "CPF é obrigatório") //RN01
    @Column(nullable = false, unique = true) // RN03
    private String cpf;
    @Past(message = "A date de nascimento deve ser anterior a data atual") //RN05
    private LocalDate dataNascimento;
    private String endereco;
}
