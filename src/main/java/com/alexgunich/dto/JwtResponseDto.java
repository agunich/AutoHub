package com.alexgunich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для ответа с JWT токеном.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {
    private String token;
}
