package com.alexgunich.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Utility class for working with JWT tokens.
 * Provides methods for generating, validating, and extracting information from JWT tokens.
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.secret}")
    private String secretKey;

    private final long expirationTime = 86400000; // Token expiration time (1 day)

    /**
     * Generates a JWT token.
     *
     * @param username the username for which the token is being generated
     * @return the generated JWT token
     */
    public String generateToken(String username) {
        try {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        } catch (Exception e) {
            logger.error("Error generating JWT token for username: {}", username, e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    /**
     * Extracts the info from the JWT token.
     *
     * @param token the JWT token
     * @return the claims contained in the token
     */
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            logger.error("Error extracting claims from token: {}", token, e);
            throw new RuntimeException("Error extracting claims from token", e);
        }
    }

    /**
     * Checks whether the token is expired.
     *
     * @param token the JWT token
     * @return true if the token is not expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        try {
            return extractClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            logger.error("Error checking token expiration for token: {}", token, e);
            throw new RuntimeException("Error checking token expiration", e);
        }
    }

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username from the token
     */
    public String extractUsername(String token) {
        try {
            return extractClaims(token).getSubject();
        } catch (Exception e) {
            logger.error("Error extracting username from token: {}", token, e);
            throw new RuntimeException("Error extracting username from token", e);
        }
    }

    /**
     * Validates the token against the username and expiration date.
     *
     * @param token    the JWT token
     * @param username the username to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, String username) {
        try {
            return (username.equals(extractUsername(token)) && !isTokenExpired(token));
        } catch (Exception e) {
            logger.error("Error validating token for user: {}", username, e);
            throw new RuntimeException("Error validating token", e);
        }
    }
}