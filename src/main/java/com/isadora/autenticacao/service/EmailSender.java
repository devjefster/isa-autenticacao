package com.isadora.autenticacao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailSender {
//    @Value("${mailgun.api.base-url}")
    private String mailGunBaseUrl;

//    @Value("${mailgun.api.key}")
    private String mailGunApiKey;

//    @Value("${mailgun.sender.email}")
    private String senderEmail;

    private final RestTemplate restTemplate = new RestTemplate();

    public void send(SimpleMailMessage... simpleMessages) throws MailException {
        for (SimpleMailMessage message : simpleMessages) {
            sendSimpleMailMessage(message);
        }
    }

    private void sendSimpleMailMessage(SimpleMailMessage message) {
        HttpHeaders headers = createAuthHeaders();
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("from", senderEmail);
        requestBody.put("to", message.getTo());
        requestBody.put("subject", message.getSubject());
        requestBody.put("text", message.getText());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                mailGunBaseUrl, HttpMethod.POST, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Email sent successfully to {}", (Object) message.getTo());
        } else {
            log.error("Failed to send email. Response: {}", response.getBody());
        }
    }

    private HttpHeaders createAuthHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = "api:" + mailGunApiKey;
        byte[] encodedAuth = auth.getBytes(StandardCharsets.ISO_8859_1);
        String authHeader = "Basic " + java.util.Base64.getEncoder().encodeToString(encodedAuth);
        headers.set("Authorization", authHeader);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        return headers;
    }
}
