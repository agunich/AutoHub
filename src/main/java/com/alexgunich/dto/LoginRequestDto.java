package com.alexgunich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для данных авторизации пользователя.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
