package com.devjefster.autenticacao.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanupTask {

    private final PasswordResetTokenService tokenService;

    @Scheduled(cron = "0 0 * * * *") // Runs every hour
    public void cleanupExpiredTokens() {
        tokenService.deleteExpiredTokens();
    }
}
