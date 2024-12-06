package com.alexgunich.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Утилита для работы с JWT.
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    private long expirationTime = 86400000; // Время жизни токена (1 день)

    /**
     * Генерация JWT токена.
     *
     * @param username имя пользователя
     * @return токен
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Извлечение информации из JWT токена.
     *
     * @param token JWT токен
     * @return информацию (claims)
     */
    public Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Проверка токена на его истечение.
     *
     * @param token JWT токен
     * @return true, если токен не истек
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    /**
     * Извлечение имени пользователя из JWT токена.
     *
     * @param token JWT токен
     * @return имя пользователя
     */
    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    /**
     * Проверка валидности токена.
     *
     * @param token JWT токен
     * @param username имя пользователя
     * @return true, если токен валиден
     */
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
