package com.isadora.autenticacao.model.repository;

import com.isadora.autenticacao.model.Login;
import com.isadora.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {

    Optional<Login> findByUsername(String username);
    Optional<Login> findByUsuario(Usuario usuario);


}
