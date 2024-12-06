package com.alexgunich.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
    private Long id;
    private Long userId;
    private Long carId;
}
