package com.empresa.crud.cliente.controller;

import com.empresa.crud.cliente.DTO.ContatoDTO;
import com.empresa.crud.cliente.model.Cliente;
import com.empresa.crud.cliente.model.Contato;
import com.empresa.crud.cliente.service.ClienteService;
import com.empresa.crud.cliente.service.ContatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContatoController.class)
class ContatoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContatoService contatoService;

    @Autowired
    private ClienteService clienteService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ContatoService contatoService() {
            return mock(ContatoService.class);
        }

        @Bean
        public ClienteService clienteService() {
            return mock(ClienteService.class);
        }
    }

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private Contato contato;
    private ContatoDTO contatoDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(1990, 5, 15));

        contato = new Contato();
        contato.setId(1L);
        contato.setTipoContato(Contato.TipoContato.TELEFONE);
        contato.setValorContato("(11) 98765-4321");
        contato.setObservacao("Celular pessoal");
        contato.setCliente(cliente);

        contatoDTO = new ContatoDTO();
        contatoDTO.setId(1L);
        contatoDTO.setTipoContato(Contato.TipoContato.TELEFONE);
        contatoDTO.setValorContato("(11) 98765-4321");
        contatoDTO.setObservacao("Celular pessoal");
        contatoDTO.setClienteId(1L);
    }

    @Test
    void testSalvarContato() throws Exception {
        // Arrange
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(contatoService.salvar(any(Contato.class))).thenReturn(contato);

        // Act & Assert
        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contatoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipoContato").value("TELEFONE"))
                .andExpect(jsonPath("$.valorContato").value("(11) 98765-4321"));

        verify(contatoService).salvar(any(Contato.class));
    }

    @Test
    void testAtualizarContato() throws Exception {
        // Arrange
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(contatoService.atualizar(eq(1L), any(Contato.class))).thenReturn(contato);

        // Act & Assert
        mockMvc.perform(put("/contatos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contatoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipoContato").value("TELEFONE"));

        verify(contatoService).atualizar(eq(1L), any(Contato.class));
    }

    @Test
    void testDeletarContato() throws Exception {
        // Arrange
        doNothing().when(contatoService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/contatos/1"))
                .andExpect(status().isNoContent());

        verify(contatoService).deletar(1L);
    }

    @Test
    void testBuscarPorClienteId() throws Exception {
        // Arrange
        List<Contato> contatos = Arrays.asList(contato);
        when(contatoService.buscarPorClienteId(1L)).thenReturn(contatos);

        // Act & Assert
        mockMvc.perform(get("/contatos/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].tipoContato").value("TELEFONE"));

        verify(contatoService).buscarPorClienteId(1L);
    }

    @Test
    void testSalvarContatoComDadosInvalidos() throws Exception {
        // Arrange
        ContatoDTO contatoInvalido = new ContatoDTO();
        contatoInvalido.setClienteId(1L);
        // Tipo e valor não preenchidos

        // Act & Assert
        mockMvc.perform(post("/contatos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contatoInvalido)))
                .andExpect(status().isBadRequest());

        verify(contatoService, never()).salvar(any(Contato.class));
    }
}

