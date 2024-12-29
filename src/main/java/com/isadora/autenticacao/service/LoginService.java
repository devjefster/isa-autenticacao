package com.isadora.autenticacao.service;

import com.isadora.autenticacao.model.Login;
import com.isadora.autenticacao.model.RoleEnum;
import com.isadora.autenticacao.model.repository.LoginRepository;
import com.isadora.autenticacao.util.exceptions.AuthenticationException;
import com.isadora.autenticacao.util.exceptions.ValidationException;
import com.isadora.usuario.model.Usuario;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "cacheLogin", key = "#username")
    public Login getLogin(String username) {
        return loginRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Usuario não encontrado com o login: " + username));
    }

    public Login criarLogin(Usuario usuario, String senha) {
        Login login = new Login(usuario.getEmail(), passwordEncoder.encode(senha), usuario, RoleEnum.USUARIO);
        return loginRepository.save(login);
    }

    public Login atualizarLogin(Usuario usuario, String novaSenha) {
        Login login = loginRepository.findByUsuario(usuario).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        return atualizarLogin(login, novaSenha);
    }

    public Login atualizarLogin(Login login, String novaSenha) {

        if (passwordEncoder.matches(novaSenha, login.getSenha())) {
            throw new ValidationException("A nova senha não pode ser a mesma que a anterior.", null);
        }
        login.setSenha(passwordEncoder.encode(novaSenha));
        return loginRepository.save(login);
    }

    @CacheEvict(value = "cacheLogin", key = "#login.username")
    public void evictCacheOnUpdate(Login login) {
        log.info("Cache evicted for login: {}", login.getUsername());
    }
}
