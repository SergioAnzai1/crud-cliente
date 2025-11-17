package com.empresa.crud.cliente.service;

import com.empresa.crud.cliente.model.Cliente;
import com.empresa.crud.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João Silva");
        cliente.setCpf("123.456.789-00");
        cliente.setDataNascimento(LocalDate.of(1990, 5, 15));
        cliente.setEndereco("Rua das Flores, 123");
    }

    @Test
    void testSalvarClienteComSucesso() {
        // Arrange
        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.empty());
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente resultado = clienteService.salvar(cliente);

        // Assert
        assertNotNull(resultado);
        assertEquals("João Silva", resultado.getNome());
        verify(clienteRepository).findByCpf(cliente.getCpf());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testSalvarClienteComCpfDuplicado() {
        // Arrange
        when(clienteRepository.findByCpf(cliente.getCpf())).thenReturn(Optional.of(cliente));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.salvar(cliente);
        });

        assertEquals("CPF já cadastrado", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testSalvarClienteNulo() {
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.salvar(null);
        });

        assertEquals("Cliente é obrigatório", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testAtualizarClienteComSucesso() {
        // Arrange
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setId(1L);
        clienteAtualizado.setNome("João Silva Atualizado");
        clienteAtualizado.setCpf("123.456.789-00");
        clienteAtualizado.setDataNascimento(LocalDate.of(1990, 5, 15));
        clienteAtualizado.setEndereco("Nova Rua, 456");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.findByCpf("123.456.789-00")).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente resultado = clienteService.atualizar(1L, clienteAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void testAtualizarClienteNaoEncontrado() {
        // Arrange
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.atualizar(999L, cliente);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void testDeletarClienteComSucesso() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> clienteService.deletar(1L));

        // Assert
        verify(clienteRepository).findById(1L);
        verify(clienteRepository).deleteById(1L);
    }

    @Test
    void testDeletarClienteNaoEncontrado() {
        // Arrange
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.deletar(999L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
        verify(clienteRepository, never()).deleteById(anyLong());
    }

    @Test
    void testBuscarTodosClientes() {
        // Arrange
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        // Act
        List<Cliente> resultado = clienteService.buscarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testBuscarPorNomeOuCpf() {
        // Arrange
        String termo = "João";
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.buscarPorNomeOuCpf(termo)).thenReturn(clientes);

        // Act
        List<Cliente> resultado = clienteService.buscarPorNomeOuCpf(termo);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(clienteRepository).buscarPorNomeOuCpf(termo);
    }

    @Test
    void testBuscarClientePorIdComSucesso() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Cliente resultado = clienteService.buscarClientePorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNome());
    }

    @Test
    void testBuscarClientePorIdNaoEncontrado() {
        // Arrange
        when(clienteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.buscarClientePorId(999L);
        });

        assertEquals("Cliente não encontrado", exception.getMessage());
    }
}

