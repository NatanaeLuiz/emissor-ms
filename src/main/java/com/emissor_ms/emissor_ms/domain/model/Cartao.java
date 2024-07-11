package com.emissor_ms.emissor_ms.domain.model;

import lombok.Data;

@Data
public class Cartao {
    private Long id;
    private String numero;
    private String nomeCliente;
    private String validade;
    private String cvv;

}
