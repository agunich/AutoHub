package com.alexgunich.validation;

import com.alexgunich.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Валидация данных пользователя.
 */
@Component
public class UserValidator {

    /**
     * Проверка данных пользователя на корректность.
     *
     * @param user объект User
     * @throws IllegalArgumentException если данные некорректны
     */
    public void validateUser(User user) {
        // Использование Optional для проверки email
        Optional.ofNullable(user.getEmail())
                .filter(email -> email.contains("@"))
                .orElseThrow(() -> new IllegalArgumentException("Неверный формат email"));

        // Проверка пароля с Optional
        Optional.ofNullable(user.getPassword())
                .filter(password -> password.length() >= 6)
                .orElseThrow(() -> new IllegalArgumentException("Пароль должен быть не менее 6 символов"));
    }
}