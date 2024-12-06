package com.alexgunich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO для данных регистрации пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
}
