package com.isadora.autenticacao.controller.dto;

import com.isadora.autenticacao.model.Login;
import com.isadora.autenticacao.model.RoleEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
public class LoginDTO implements UserDetails {

    @Getter
    private Long id;
    private String username;
    private String password;
    @Getter
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public static LoginDTO map(Login login) {
        LoginDTO dto = new LoginDTO();
        dto.setPassword(login.getSenha());
        dto.setUsername(login.getUsername());
        dto.setRole(login.getRole());
        return dto;
    }
}
