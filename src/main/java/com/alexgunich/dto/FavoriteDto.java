package com.alexgunich.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for a favorite entry.
 * Represents the favorite record associated with a user and a car.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {

    /**
     * The ID of the user who added the car to favorites.
     * This field is required.
     */
    @NotNull(message = "User ID is required")
    private Long userId;

    /**
     * The ID of the car that is marked as a favorite.
     * This field is required.
     */
    @NotNull(message = "Car ID is required")
    private Long carId;
}