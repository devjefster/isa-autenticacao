package com.devjefster.usuario.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtualizarUsuarioDTO extends CriarUsuarioDTO {


    private String senha;
    private String tiktokUser;
    private String instagramUser;

    private MultipartFile fotoPerfil;

    private String novaSenha;
}
