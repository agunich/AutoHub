package com.alexgunich.config;

import com.alexgunich.service.UserService;
import com.alexgunich.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Конфигурация безопасности для приложения.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;
    private final JwtFilter jwtFilter;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(UserService userService, JwtFilter jwtFilter, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод для создания AuthenticationManager.
     * Используется для настройки аутентификации.
     *
     * @return AuthenticationManager
     * @throws Exception ошибка конфигурации
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    /**
     * Конфигурация фильтрации безопасности с использованием JWT.
     *
     * @param http безопасность http
     * @return объект SecurityFilterChain
     * @throws Exception ошибка конфигурации
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
//                .requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll() // Разрешаем доступ к этим маршрутам
                // FIXME перед пулом на основную ветку откатить POST методы на маршруты "/auth/login", "/auth/register" !
                .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()  // Разрешаем доступ ко всем GET-запросам
                .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);  // Добавляем фильтр JWT

        return http.build();
    }
}