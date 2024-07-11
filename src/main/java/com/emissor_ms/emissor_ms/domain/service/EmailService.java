package com.emissor_ms.emissor_ms.domain.service;

public interface EmailService {

    void sendEmail(String to, String subject, String text);
}
