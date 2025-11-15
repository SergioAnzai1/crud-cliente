package com.empresa.crud.cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

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
    @Column(nullable = false, length = 100) //Protejer o banco de dados de entrada nulas //RN04
    private String nome;

    @NotBlank(message = "CPF é obrigatório") //RN01
    @Column(nullable = false, unique = true, length = 14) //RN03
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A date de nascimento deve ser anterior a data atual") //RN05
    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(length = 255)
    private String endereco;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true) //RN07
    private List<Contato> contatos;
}
