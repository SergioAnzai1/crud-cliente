package com.empresa.crud.cliente.service;

import com.empresa.crud.cliente.model.Cliente;
import com.empresa.crud.cliente.model.Contato;
import com.empresa.crud.cliente.repository.ContatoRepository;
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
class ContatoServiceTest {

    @Mock
    private ContatoRepository contatoRepository;

    @Mock
    private ClienteService clienteService;

    @InjectMocks
    private ContatoService contatoService;

    private Cliente cliente;
    private Contato contato;

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
    }

    @Test
    void testSalvarContatoComSucesso() {
        // Arrange
        doNothing().when(clienteService).validarClienteNulo(any(Cliente.class));
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        // Act
        Contato resultado = contatoService.salvar(contato);

        // Assert
        assertNotNull(resultado);
        assertEquals(Contato.TipoContato.TELEFONE, resultado.getTipoContato());
        assertEquals("(11) 98765-4321", resultado.getValorContato());
        verify(clienteService).validarClienteNulo(cliente);
        verify(clienteService).buscarClientePorId(1L);
        verify(contatoRepository).save(contato);
    }

    @Test
    void testAtualizarContatoComSucesso() {
        // Arrange
        Contato contatoAtualizado = new Contato();
        contatoAtualizado.setTipoContato(Contato.TipoContato.EMAIL);
        contatoAtualizado.setValorContato("joao@email.com");
        contatoAtualizado.setObservacao("E-mail principal");
        contatoAtualizado.setCliente(cliente);

        doNothing().when(clienteService).validarClienteNulo(any(Cliente.class));
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        when(contatoRepository.save(any(Contato.class))).thenReturn(contato);

        // Act
        Contato resultado = contatoService.atualizar(1L, contatoAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(contatoRepository).findById(1L);
        verify(contatoRepository).save(any(Contato.class));
    }

    @Test
    void testAtualizarContatoNaoEncontrado() {
        // Arrange
        when(contatoRepository.findById(999L)).thenReturn(Optional.empty());
        doNothing().when(clienteService).validarClienteNulo(any(Cliente.class));
        when(clienteService.buscarClientePorId(1L)).thenReturn(cliente);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.atualizar(999L, contato);
        });

        assertEquals("Contato não encontrado", exception.getMessage());
        verify(contatoRepository, never()).save(any(Contato.class));
    }

    @Test
    void testDeletarContatoComSucesso() {
        // Arrange
        when(contatoRepository.findById(1L)).thenReturn(Optional.of(contato));
        doNothing().when(contatoRepository).delete(any(Contato.class));

        // Act
        assertDoesNotThrow(() -> contatoService.deletar(1L));

        // Assert
        verify(contatoRepository).findById(1L);
        verify(contatoRepository).delete(contato);
    }

    @Test
    void testDeletarContatoNaoEncontrado() {
        // Arrange
        when(contatoRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            contatoService.deletar(999L);
        });

        assertEquals("Contato não encontrado", exception.getMessage());
        verify(contatoRepository, never()).delete(any(Contato.class));
    }

    @Test
    void testBuscarPorClienteId() {
        // Arrange
        List<Contato> contatos = Arrays.asList(contato);
        when(contatoRepository.findByClienteId(1L)).thenReturn(contatos);

        // Act
        List<Contato> resultado = contatoService.buscarPorClienteId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(contatoRepository).findByClienteId(1L);
    }
}

