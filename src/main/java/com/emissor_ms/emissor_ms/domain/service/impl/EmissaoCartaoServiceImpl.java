package com.emissor_ms.emissor_ms.domain.service.impl;

import com.emissor_ms.emissor_ms.application.client.CartoesFeignClient;
import com.emissor_ms.emissor_ms.application.client.Proposta;
import com.emissor_ms.emissor_ms.domain.exception.NotFoundException;
import com.emissor_ms.emissor_ms.domain.model.Cartao;
import com.emissor_ms.emissor_ms.domain.service.EmissaoCartaoService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoCartaoServiceImpl implements EmissaoCartaoService {

    private static final String PROPOSTA_APROVADA = "APROVADO";
    @Autowired
    private CartoesFeignClient cartoesFeignClient;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public Cartao emitirCartao(Long propostaId) {
        try {
            Proposta proposta = cartoesFeignClient.buscarPropostaPorID(propostaId);

            if (!PROPOSTA_APROVADA.equals(proposta.status())) {
                throw new IllegalArgumentException("Proposta não está aprovada: " + propostaId);
            }

            Cartao cartao = new Cartao();

            cartao.setNumero("1234-5678-9012-3456");
            cartao.setNomeCliente(proposta.cliente().nome());
            cartao.setValidade("12/25");
            cartao.setCvv("123");

            enviarEmailCartao(proposta.cliente().email(), cartao);
            return cartao;
        } catch (FeignException.NotFound e) {
            // Exceção específica para quando a proposta não é encontrada
            throw new NotFoundException("Proposta não encontrada: " + propostaId);
        } catch (FeignException e) {
            // Exceções específicas do FeignClient (Caso de problema na busca da proposta)
            throw new RuntimeException("Erro ao buscar proposta: " + propostaId, e);
        } catch (Exception e) {
            // Outras exceções genéricas
            throw new RuntimeException("Erro ao emitir cartão", e);
        }
    }

    private void enviarEmailCartao(String email, Cartao cartao) {
        String emailContent = "Seu cartão foi emitido com sucesso!\n" +
                "Número: " + cartao.getNumero() + "\n" +
                "Nome: " + cartao.getNomeCliente() + "\n" +
                "Validade: " + cartao.getValidade() + "\n" +
                "CVV: " + cartao.getCvv();

        emailService.sendEmail(email, "Emissão de Cartão", emailContent);
    }
}
