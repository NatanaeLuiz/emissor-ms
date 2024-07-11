package com.emissor_ms.emissor_ms.controller;

import com.emissor_ms.emissor_ms.application.controller.EmissaoController;
import com.emissor_ms.emissor_ms.domain.model.Cartao;
import com.emissor_ms.emissor_ms.domain.service.EmissaoCartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmissaoControllerTest {

    @Mock
    private EmissaoCartaoService emissaoCartaoService;

    @InjectMocks
    private EmissaoController emissaoController;

    @Autowired
    private MockMvc mockMvc;

    private Cartao mockCartao;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(emissaoController).build();

        mockCartao = new Cartao();
        mockCartao.setId(1L);
        mockCartao.setNumero("1234-5678-9012-3456");
        mockCartao.setValidade("12/25");
        mockCartao.setCvv("123");
    }

    @Test
    void testEmitirCartao() throws Exception {

        when(emissaoCartaoService.emitirCartao(anyLong())).thenReturn(mockCartao);

        mockMvc.perform(post("/api/emissao/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.numero").value("1234-5678-9012-3456"))
                .andExpect(jsonPath("$.validade").value("12/25"))
                .andExpect(jsonPath("$.cvv").value("123"));
    }
}
