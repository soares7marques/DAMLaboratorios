package com.example.meu_primeiroSpringBoot.Security;

import java.sql.Date;

import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;

public class JwtToken {
    
    private static final SecretKey key = Jwts.SIG.HS256.key().build();
    private static final long EXPIRATION_TIME = 86400000; // 1 dia em milissegundos

    // gerar token e fazer autenticação do utente
    public static String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // Extrair username/email do token
    public static String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
