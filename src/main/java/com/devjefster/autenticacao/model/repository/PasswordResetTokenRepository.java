package com.devjefster.autenticacao.model.repository;

import com.devjefster.autenticacao.model.TokenResetSenha;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasswordResetTokenRepository extends CrudRepository<TokenResetSenha, Long> {

    Optional<TokenResetSenha> findByTokenAndExpiryDateAfter(String token, LocalDateTime now);

    void deleteByToken(String token);

    void deleteAllByExpiryDateBefore(LocalDateTime expiryDate);
}
