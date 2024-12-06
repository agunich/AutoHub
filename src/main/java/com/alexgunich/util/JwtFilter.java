package com.alexgunich.util;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Фильтр для обработки JWT токенов в запросах.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    /**
     * Фильтрация запросов для проверки JWT.
     *
     * @param request запрос
     * @param response ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException ошибка фильтрации
     * @throws IOException ошибка ввода/вывода
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String token = request.getHeader("Authorization");

        String username = null;
        if (token != null && token.startsWith("Bearer ")) {
            try {
                username = jwtUtil.extractUsername(token.substring(7));
            } catch (Exception e) {
                logger.error("Ошибка при извлечении имени пользователя из токена", e);
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtUtil.validateToken(token.substring(7), username)) {
                // Если токен валиден, установить аутентификацию в контексте безопасности
                var authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}