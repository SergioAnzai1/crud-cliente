package com.empresa.crud.cliente.controller;

import com.empresa.crud.cliente.DTO.ClienteDTO;
import com.empresa.crud.cliente.model.Cliente;
import com.empresa.crud.cliente.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(1990, 5, 15));
        cliente.setEndereco("Rua das Flores, 123");

        clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("João Silva");
        clienteDTO.setCpf("123.456.789-00");
        clienteDTO.setDataNascimento(LocalDate.of(1990, 5, 15));
        clienteDTO.setEndereco("Rua das Flores, 123");
    }

    @Test
    void testSalvarCliente() throws Exception {
        // Arrange
        when(clienteService.salvar(any(Cliente.class))).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"));

        verify(clienteService).salvar(any(Cliente.class));
    }

    @Test
    void testAtualizarCliente() throws Exception {
        // Arrange
        when(clienteService.atualizar(eq(1L), any(Cliente.class))).thenReturn(cliente);

        // Act & Assert
        mockMvc.perform(put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Silva"));

        verify(clienteService).atualizar(eq(1L), any(Cliente.class));
    }

    @Test
    void testDeletarCliente() throws Exception {
        // Arrange
        doNothing().when(clienteService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/clientes/1"))
                .andExpect(status().isNoContent());

        verify(clienteService).deletar(1L);
    }

    @Test
    void testBuscarTodosClientes() throws Exception {
        // Arrange
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.buscarTodos()).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));

        verify(clienteService).buscarTodos();
    }

    @Test
    void testBuscarPorNomeOuCpf() throws Exception {
        // Arrange
        String termo = "João";
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteService.buscarPorNomeOuCpf(termo)).thenReturn(clientes);

        // Act & Assert
        mockMvc.perform(get("/clientes/buscar")
                .param("termo", termo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].nome").value("João Silva"));

        verify(clienteService).buscarPorNomeOuCpf(termo);
    }

    @Test
    void testSalvarClienteComDadosInvalidos() throws Exception {
        // Arrange
        ClienteDTO clienteInvalido = new ClienteDTO();
        clienteInvalido.setNome(""); // Nome vazio

        // Act & Assert
        mockMvc.perform(post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteInvalido)))
                .andExpect(status().isBadRequest());

        verify(clienteService, never()).salvar(any(Cliente.class));
    }
}

