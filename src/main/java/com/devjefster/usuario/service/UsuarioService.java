package com.devjefster.usuario.service;

import com.devjefster.autenticacao.service.LoginService;
import com.devjefster.autenticacao.util.exceptions.UsuarioException;
import com.devjefster.autenticacao.util.exceptions.ValidationException;
import com.devjefster.autenticacao.util.validator.EmailValidator;
import com.devjefster.autenticacao.util.validator.PasswordValidator;
import com.devjefster.autenticacao.util.validator.PhoneNumberValidator;
import com.devjefster.usuario.model.Usuario;
import com.devjefster.usuario.model.dto.AtualizarUsuarioDTO;
import com.devjefster.usuario.model.dto.CriarUsuarioDTO;
import com.devjefster.usuario.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;
    private final LoginService loginService;


    public Usuario create(CriarUsuarioDTO criarUsuarioDTO) {
        validateUserData(criarUsuarioDTO);  // Refactored validation logic

        Usuario usuario = criarUsuarioDTO.map();
        usuarioRepository.save(usuario);
        loginService.criarLogin(usuario, criarUsuarioDTO.getPassword());
        return usuario;
    }

    public Usuario updateUserProfile(Usuario usuario, AtualizarUsuarioDTO atualizarUsuarioDTO) {
        validateUserData(atualizarUsuarioDTO);

        usuario.setNome(atualizarUsuarioDTO.getNome());
        usuario.setTelefone(atualizarUsuarioDTO.getTelefone());
        loginService.atualizarLogin(usuario, atualizarUsuarioDTO.getNovaSenha());

        return usuarioRepository.save(usuario);
    }

    @CacheEvict(value = "cacheUsuario", key = "#id")
    public void evictCacheOnUserUpdate(Long id) {
        log.info("Cache evicted for user with id: {}", id);
    }

    private void validateUserData(CriarUsuarioDTO dto) {
        if (!EmailValidator.isValidEmailDomain(dto.getEmail())) {
            throw new ValidationException("Email inválido", dto.getEmail());
        }
        if (PasswordValidator.isWeakPassword(dto.getPassword())) {
            throw new ValidationException("Senha fraca", null);
        }
        if (PhoneNumberValidator.isValidPhoneNumber(dto.getTelefone())) {
            throw new ValidationException("Telefone inválido", dto.getTelefone());
        }
    }

    @Cacheable(value = "cacheUsuario", key = "#id")
    public Usuario buscarUsuarioPeloId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioException("Usuário não encontrado com ID: " + id));
    }

    public Usuario buscarPeloEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}