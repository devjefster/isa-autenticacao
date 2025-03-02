package com.devjefster.autenticacao.config;

import com.devjefster.autenticacao.model.Login;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component

public class JwtUtil {

    @Value("${security.jwt.secret}")
    protected String secretKey;

    @Value("${security.jwt.issuer}")
    protected String issuer;

    @Value("${security.jwt.expiration-time}")
    protected String expirationTime;

    public String generateToken(Login login) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", login.getRole());
        return createToken(claims, login.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        Date issuedAt = new Date(currentTimeMillis);
        Date expiration = new Date(currentTimeMillis + Long.parseLong(expirationTime));
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .id(UUID.randomUUID().toString()) // `jti`: unique token ID
                .notBefore(issuedAt)
                .issuedAt(issuedAt)
                .issuer(issuer)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Claims claims = extractAllClaims(token);

            if (isTokenExpired(claims)) {
                throw new IllegalStateException("Token is expired");
            }

            if (!issuer.equals(claims.getIssuer())) {
                throw new IllegalStateException("Invalid token issuer");
            }

            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public List<String> extractRoles(String token) {
        return Collections.singletonList(extractAllClaims(token).get("role", String.class));
    }
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    protected Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    protected SecretKey getSigningKey() {
        byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }


}
