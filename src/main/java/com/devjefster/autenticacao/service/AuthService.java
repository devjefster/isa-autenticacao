package com.devjefster.autenticacao.service;


import com.devjefster.autenticacao.config.JwtUtil;
import com.devjefster.autenticacao.controller.dto.LoginDTO;
import com.devjefster.autenticacao.controller.dto.TokenDTO;
import com.devjefster.autenticacao.model.Login;
import com.devjefster.autenticacao.util.exceptions.AuthenticationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements UserDetailsService {


    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final LoginService loginService;
    private final PasswordResetTokenService tokenService;
    private final EmailSender mailSender;

    public TokenDTO login(LoginDTO loginDTO) {
        Login login = loginService.getLogin(loginDTO.getUsername());

        if (!passwordEncoder.matches(loginDTO.getPassword(), login.getSenha())) {
            throw new AuthenticationException("Senha inválida");
        }

        return new TokenDTO(login.getUsuario().getId(), jwtUtil.generateToken(login));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Carregando detalhes do usuário: {}", username);
        return LoginDTO.map(loginService.getLogin(username));
    }

    public void initiatePasswordReset(String email) {
        Login login = loginService.getLogin(email);
        String token = tokenService.createPasswordResetToken(login);

        String resetLink = "https://yourdomain.com/reset-password?token=" + token;
        sendPasswordResetEmail(email, resetLink);
    }

    private void sendPasswordResetEmail(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Request");
        message.setText("Click on the following link to reset your password: " + resetLink);
        mailSender.send(message);
    }

    public void resetPassword(String token, String newPassword) {
        Login login = tokenService.validatePasswordResetToken(token);
        loginService.atualizarLogin(login, newPassword);
        tokenService.deleteToken(token); // Invalidate the token after reset
        sendPasswordResetConfirmation(login.getUsername()); // Notify the user
    }

    private void sendPasswordResetConfirmation(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Password Reset Confirmation");
        message.setText("Your password has been successfully reset.");
        mailSender.send(message);
    }

    public TokenDTO validateToken(String bearer) {
        String token = bearer.substring(7);
        if (jwtUtil.validateToken(token)) {
            throw new AuthenticationException("Token inválido ou expirado");
        }

        String username = jwtUtil.extractUsername(token);
        Login login = loginService.getLogin(username);

        if (login == null) {
            throw new AuthenticationException("Usuário não encontrado para o token fornecido");
        }

        return new TokenDTO(login.getUsuario().getId(), token);
    }

}
