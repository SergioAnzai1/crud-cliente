package com.empresa.crud.cliente.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contatos")
@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor



public class Contato {
    public enum TipoContato {
        TELEFONE,
        EMAIL,
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tipo de contato é obrigatório")
    @Column(nullable = false, length = 50)
    private TipoContato tipoContato;

    @NotBlank(message = "Valor de contato é obrigatório")
    @Column(nullable = false, length = 100)
    private String valorContato;

    @Column(length = 255)
    private String observacao;

    @ManyToOne //RN06
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;   
}
