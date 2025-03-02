package com.isadora.autenticacao.controller;

import com.isadora.autenticacao.controller.dto.LoginDTO;
import com.isadora.autenticacao.controller.dto.TokenDTO;
import com.isadora.autenticacao.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO loginRequest) {
        log.info("Login request, {}",loginRequest);
        return ResponseEntity.ok(service.login(loginRequest));
    }

    @GetMapping("/validar-token")
    public ResponseEntity<TokenDTO> validateToken(@RequestHeader(name = "Authorization") String token) {
        log.info("Validar Token");
        return ResponseEntity.ok(service.validateToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        log.info("Logout da aplicação");
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return ResponseEntity.ok("Logged out successfully");
    }

    @PostMapping("/esqueceu-senha")
    public ResponseEntity<String> esqueceuSenha(@RequestParam String email) {
        log.info("Esqueceu a senha");
        service.initiatePasswordReset(email);
        return ResponseEntity.ok("Email enviado com sucesso");
    }

    @PostMapping("/reset-senha")
    public ResponseEntity<String> resetSenha(@RequestParam String token, @RequestBody String newPassword) {
        service.resetPassword(token, newPassword);
        return ResponseEntity.ok("Senha redefinida com sucesso");
    }
}
