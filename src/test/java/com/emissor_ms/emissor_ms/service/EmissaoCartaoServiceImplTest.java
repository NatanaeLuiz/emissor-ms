package com.emissor_ms.emissor_ms.service;

import com.emissor_ms.emissor_ms.application.client.CartoesFeignClient;
import com.emissor_ms.emissor_ms.application.client.Cliente;
import com.emissor_ms.emissor_ms.application.client.Proposta;
import com.emissor_ms.emissor_ms.domain.exception.NotFoundException;
import com.emissor_ms.emissor_ms.domain.model.Cartao;
import com.emissor_ms.emissor_ms.domain.service.impl.EmailServiceImpl;
import com.emissor_ms.emissor_ms.domain.service.impl.EmissaoCartaoServiceImpl;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmissaoCartaoServiceImplTest {

    @Mock
    private CartoesFeignClient cartoesFeignClient;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private EmissaoCartaoServiceImpl emissaoCartaoService;

    private Proposta propostaAprovada;


    @BeforeEach
    void setUp() {
        Cliente cliente = new Cliente(1L, "Cliente Teste", "000111222333", "cliente@teste.com");

        propostaAprovada = new Proposta(1L, "Nome Proposta", "123456", 1000.0, true,"APROVADO", cliente);
    }

    @Test
    void emitirCartaoPropostaAprovada() {
        when(cartoesFeignClient.buscarPropostaPorID(1L)).thenReturn(propostaAprovada);

        Cartao cartao = emissaoCartaoService.emitirCartao(1L);

        assertNotNull(cartao);
        assertEquals("1234-5678-9012-3456", cartao.getNumero());
        assertEquals("Cliente Teste", cartao.getNomeCliente());
        assertEquals("12/25", cartao.getValidade());
        assertEquals("123", cartao.getCvv());

        verify(emailService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void emitirCartaoPropostaNaoEncontrada() {
        when(cartoesFeignClient.buscarPropostaPorID(1L)).thenThrow(FeignException.NotFound.class);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            emissaoCartaoService.emitirCartao(1L);
        });

        assertEquals("Proposta não encontrada: 1", exception.getMessage());
    }

    @Test
    void emitirCartaoErroFeignException() {
        when(cartoesFeignClient.buscarPropostaPorID(1L)).thenThrow(FeignException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emissaoCartaoService.emitirCartao(1L);
        });

        assertEquals("Erro ao buscar proposta: 1", exception.getMessage());
    }

    @Test
    void emitirCartaoErroGenerico() {
        when(cartoesFeignClient.buscarPropostaPorID(1L)).thenThrow(RuntimeException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            emissaoCartaoService.emitirCartao(1L);
        });

        assertEquals("Erro ao emitir cartão", exception.getMessage());
    }
}
