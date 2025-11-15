package com.empresa.crud.cliente.DTO;

import com.empresa.crud.cliente.model.Cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    @NotBlank(message = "Nome é obrigatório") //RN01
    private String nome;
    @NotBlank(message = "CPF é obrigatório") //RN01
    private String cpf;
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "A date de nascimento deve ser anterior a data atual") //RN05
    private LocalDate dataNascimento;
    private String endereco;

    public static ClienteDTO fromEntity(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setEndereco(cliente.getEndereco());
        return dto;
    }

    public Cliente toEntity() {
        Cliente cliente = new Cliente();
        cliente.setId(this.id);
        cliente.setNome(this.nome);
        cliente.setCpf(this.cpf);
        cliente.setDataNascimento(this.dataNascimento);
        cliente.setEndereco(this.endereco);
        return cliente;
    }
}
