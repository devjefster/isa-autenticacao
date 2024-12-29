package com.isadora.usuario.facade;


import com.isadora.usuario.model.Usuario;
import com.isadora.usuario.model.dto.AtualizarUsuarioDTO;
import com.isadora.usuario.model.dto.CriarUsuarioDTO;
import com.isadora.usuario.model.dto.UsuarioDTO;
import com.isadora.usuario.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UsuarioFacade {

    private final UsuarioService usuarioService;

    public UsuarioDTO buscarPeloId(Long id) {
        Usuario usuario = usuarioService.buscarUsuarioPeloId(id);
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getTelefone(), usuario.getEmail(), usuario.getInstagramUser(), usuario.getInstagramUser(), usuario.getImagemId());
    }

    public UsuarioDTO update(Long id, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        Usuario usuario = usuarioService.buscarUsuarioPeloId(id);
        usuario = usuarioService.updateUserProfile(usuario, atualizarUsuarioDTO);
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getTelefone(), usuario.getEmail(), usuario.getInstagramUser(), usuario.getInstagramUser(), usuario.getImagemId());
    }

    public UsuarioDTO create(CriarUsuarioDTO criarUsuarioDTO) {
        Usuario usuario = usuarioService.create(criarUsuarioDTO);
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getTelefone(), usuario.getEmail(), usuario.getInstagramUser(), usuario.getInstagramUser(), null);

    }

    public Usuario buscarPeloEmail(String email) {
        return usuarioService.buscarPeloEmail(email);
    }
}
