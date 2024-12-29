package com.isadora.autenticacao.service;

import com.isadora.autenticacao.model.Login;
import com.isadora.autenticacao.model.TokenResetSenha;
import com.isadora.autenticacao.model.repository.PasswordResetTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;


    public String createPasswordResetToken(Login login) {
        String token = UUID.randomUUID().toString();
        TokenResetSenha resetToken = new TokenResetSenha(token, login, LocalDateTime.now().plusHours(1));
        tokenRepository.save(resetToken);
        return token;
    }

    public Login validatePasswordResetToken(String token) {
        TokenResetSenha resetToken = tokenRepository.findByTokenAndExpiryDateAfter(token, LocalDateTime.now())
                .orElseThrow(() -> new IllegalArgumentException("Token inválido ou expirado"));

        return resetToken.getLogin();
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    public void deleteExpiredTokens() {
        tokenRepository.deleteAllByExpiryDateBefore(LocalDateTime.now());
    }
}
