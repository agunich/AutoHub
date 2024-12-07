package com.alexgunich.util;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Filter for processing JWT tokens in incoming requests.
 * This filter intercepts requests to extract and validate JWT tokens,
 * then establishes user authentication in the security context.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filters requests to check the JWT token in the Authorization header.
     * If the token is valid, it sets authentication for the user in the security context.
     *
     * @param request the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain to pass the request and response
     * @throws ServletException if the filter encounters an error
     * @throws IOException if an I/O error occurs during the filter process
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");

        String username = null;
        if (token != null && token.startsWith("Bearer ")) {
            try {
                username = jwtUtil.extractUsername(token.substring(7));
                logger.info("Extracted username: {}", username);
            } catch (Exception e) {
                logger.error("Error extracting username from token", e);
            }
        } else {
            logger.warn("No Bearer token found in Authorization header");
        }

        // If the username is extracted and no authentication is set, validate the token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                if (jwtUtil.validateToken(token.substring(7), username)) {
                    // If the token is valid, set the authentication in the security context
                    var authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    logger.info("Authentication set for user: {}", username);
                } else {
                    logger.error("Invalid token for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Error during token validation for user: {}", username, e);
            }
        }

        filterChain.doFilter(request, response);
    }
}