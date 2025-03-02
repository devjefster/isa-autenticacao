package com.devjefster.autenticacao.config;

import com.devjefster.autenticacao.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AutenticacaoSecurityConfig {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtRequestFilter jwtRequestFilter;

    @Value("${security.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Value("${security.cors.allowed-methods}")
    private String[] allowedMethods;

    @Value("${security.cors.allowed-headers}")
    private String[] allowedHeaders;

    @Value("${security.cors.exposed-headers}")
    private String[] exposedHeaders;

    @Value("${security.cors.allow-credentials}")
    private boolean allowCredentials;

    @Value("${security.cors.max-age}")
    private long maxAge;

    @Bean(name = "authSecurityFilterChain")
    public SecurityFilterChain authSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
                    configuration.setAllowedMethods(Arrays.asList(allowedMethods));
                    configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
                    configuration.setExposedHeaders(Arrays.asList(exposedHeaders));
                    configuration.setAllowCredentials(allowCredentials);
                    configuration.setMaxAge(maxAge);
                    return configuration;
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/usuario/**").permitAll() // Permit all access to /auth/welcome
                        .requestMatchers("/api/auth/login").permitAll() // Permit all access to /auth/welcome
                        .requestMatchers("/api/auth/esqueceu-senha").permitAll() // Permit all access to /auth/welcome
                        .anyRequest().permitAll() // Require authentication for /auth/user/**
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }


    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder);
    }

}
