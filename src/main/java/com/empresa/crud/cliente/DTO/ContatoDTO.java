package com.empresa.crud.cliente.DTO;

import com.empresa.crud.cliente.model.Contato;
import com.empresa.crud.cliente.model.Contato.TipoContato;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoDTO {

    private Long id;
    @NotNull(message = "Tipo de contato é obrigatório")
    private TipoContato tipoContato;

    @NotBlank(message = "Valor de contato é obrigatório")
    private String valorContato;

    private String observacao;

    private Long clienteId;

    public static ContatoDTO fromEntity(Contato contato) {
        ContatoDTO dto = new ContatoDTO();
        dto.setId(contato.getId());
        dto.setTipoContato(contato.getTipoContato());
        dto.setValorContato(contato.getValorContato());
        dto.setObservacao(contato.getObservacao());
        dto.setClienteId(contato.getCliente().getId());
        return dto;
    }
    public Contato toEntity() {
        Contato contato = new Contato();
        contato.setId(this.id);
        contato.setTipoContato(this.tipoContato);
        contato.setValorContato(this.valorContato);
        contato.setObservacao(this.observacao);
        return contato;
    }

}
