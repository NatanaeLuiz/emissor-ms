package com.emissor_ms.emissor_ms.application.controller;

import com.emissor_ms.emissor_ms.domain.model.Cartao;
import com.emissor_ms.emissor_ms.domain.service.EmissaoCartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/emissao")
public class EmissaoController {

    @Autowired
    private EmissaoCartaoService emissaoCartaoService;

    @PostMapping("/{propostaId}")
    public ResponseEntity<Cartao> emitirCartao(@PathVariable Long propostaId) {
        Cartao cartao = emissaoCartaoService.emitirCartao(propostaId);
        return new ResponseEntity<>(cartao, HttpStatus.CREATED);
    }
}
