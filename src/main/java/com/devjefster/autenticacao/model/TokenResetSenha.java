package com.devjefster.autenticacao.model;

import com.devjefster.common.model.EntidadeBase;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class TokenResetSenha extends EntidadeBase {


    private String token;

    @OneToOne
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;

    private LocalDateTime expiryDate;

    public TokenResetSenha(String token, Login login, LocalDateTime expiryDate) {
        this.token = token;
        this.login = login;
        this.expiryDate = expiryDate;
    }

}
