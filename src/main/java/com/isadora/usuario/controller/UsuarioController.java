package com.isadora.usuario.controller;

import com.isadora.usuario.facade.UsuarioFacade;
import com.isadora.usuario.model.Usuario;
import com.isadora.usuario.model.dto.AtualizarUsuarioDTO;
import com.isadora.usuario.model.dto.CriarUsuarioDTO;
import com.isadora.usuario.model.dto.UsuarioDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioFacade facade;

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO createUser(@RequestBody @Valid CriarUsuarioDTO criarUsuarioDTO) {
        log.info("Criando usuário");
        return facade.create(criarUsuarioDTO);
    }

    @PutMapping("/{id}/atualizar")
    public UsuarioDTO updateProfile(Long id,
                                    @Valid @ModelAttribute AtualizarUsuarioDTO atualizarUsuarioDTO) {
        log.info("Atualizando usuário pelo Id: {}", id);
        return facade.update(id, atualizarUsuarioDTO);
    }


    @GetMapping("/{id}")
    public UsuarioDTO getUserById(@PathVariable Long id) {
        log.info("Buscando usuário pelo Id: {}", id);
        return facade.buscarPeloId(id);
    }

    @GetMapping
    public Usuario getUserByEmail(@RequestParam String email) {
        log.info("Buscando usuário pelo email: {}", email);
        return facade.buscarPeloEmail(email);
    }
}
