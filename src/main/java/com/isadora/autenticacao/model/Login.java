package com.isadora.autenticacao.model;

import com.isadora.common.model.EntidadeBase;
import com.isadora.usuario.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table
@Data
@NoArgsConstructor
public class Login extends EntidadeBase {

    public Login(String username, String senha, Usuario usuario, RoleEnum role) {
        this.username = username;
        this.senha = senha;
        this.usuario = usuario;
        this.role = role;
    }

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String senha;

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

}
