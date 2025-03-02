package com.devjefster.autenticacao.model.repository;

import com.devjefster.autenticacao.model.Login;
import com.devjefster.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUsername(String username);
    Optional<Login> findByUsuario(Usuario usuario);


}
