package com.emissor_ms.emissor_ms.application.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cartoes-ms", url = "http://localhost:8090")
public interface CartoesFeignClient {

    @GetMapping("api/proposta/buscar/{idProposta}")
    Proposta buscarPropostaPorID(@PathVariable("idProposta") Long idProposta);
}
