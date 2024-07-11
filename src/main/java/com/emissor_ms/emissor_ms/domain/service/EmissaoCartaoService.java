package com.emissor_ms.emissor_ms.domain.service;

import com.emissor_ms.emissor_ms.domain.model.Cartao;

public interface EmissaoCartaoService {

    public Cartao emitirCartao(Long propostaId);
}
