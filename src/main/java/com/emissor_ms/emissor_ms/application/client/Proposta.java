package com.emissor_ms.emissor_ms.application.client;


public record Proposta(
        Long id,
        String nomeProposta,
        String numeroConta,
        Double limite,
        Boolean ativo,
        String status,
        Cliente cliente
) {
}
