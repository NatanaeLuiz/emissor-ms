package com.emissor_ms.emissor_ms.application.client;

public record Cliente (
    Long id,
    String nome,
    String cpf,
    String email
){
}