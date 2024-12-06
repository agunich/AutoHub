package com.alexgunich.controller;

import com.alexgunich.model.User;
import com.alexgunich.service.UserService;
import com.alexgunich.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для регистрации и логина пользователей.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user данные нового пользователя
     * @return ResponseEntity с результатом регистрации
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        user.setPassword(user.getPassword());  // Здесь можно добавить шифрование пароля
        userService.save(user);
        return ResponseEntity.ok("Пользователь зарегистрирован успешно");
    }

    /**
     * Логин пользователя и получение JWT токена.
     *
     * @param user данные пользователя
     * @return ResponseEntity с JWT токеном
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User authenticatedUser = (User) userService.loadUserByUsername(user.getEmail());

        if (authenticatedUser != null) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok("Bearer " + token);
        }

        return ResponseEntity.status(401).body("Неверные учетные данные");
    }
}