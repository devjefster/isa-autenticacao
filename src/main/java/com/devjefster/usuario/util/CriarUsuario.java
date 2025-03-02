package com.devjefster.usuario.util;

import com.devjefster.usuario.controller.UsuarioController;
import com.devjefster.usuario.model.dto.CriarUsuarioDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CriarUsuario {

    private final UsuarioController controller;


    @PostConstruct
    public void init() {

        if (controller.getUserByEmail("admin@gmail.com") == null) {
            CriarUsuarioDTO dto = new CriarUsuarioDTO();
            dto.setEmail("admin@gmail.com");
            dto.setNome("Admin");
            dto.setPassword("admin123");
            dto.setTelefone("62991115292");

            controller.createUser(dto);
        }
        if (controller.getUserByEmail("isacarolinna23@gmail.com") == null) {
            CriarUsuarioDTO dto = new CriarUsuarioDTO();
            dto.setEmail("isacarolinna23@gmail.com");
            dto.setNome("Isadora Carolina");
            dto.setPassword("admin123");
            dto.setTelefone("62991115292");

            controller.createUser(dto);
        }

    }
}
