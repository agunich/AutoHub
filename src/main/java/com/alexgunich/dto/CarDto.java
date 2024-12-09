package com.alexgunich.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for representing a car.
 * Used for transferring car data between layers.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    @NotNull
    private Long id;

    /**
     * The ID of the user who owns the car.
     * Cannot be null.
     */
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    /**
     * The brand of the car.
     * Cannot be null or empty. Must be between 1 and 50 characters.
     */
    @NotNull(message = "Brand cannot be null")
    @Size(min = 1, max = 50, message = "Brand must be between 1 and 50 characters")
    private String brand;

    /**
     * The model of the car.
     * Cannot be null or empty. Must be between 1 and 50 characters.
     */
    @NotNull(message = "Model cannot be null")
    @Size(min = 1, max = 50, message = "Model must be between 1 and 50 characters")
    private String model;

    /**
     * The year the car was manufactured.
     * Must be a valid year (greater than 1900).
     */
    @NotNull(message = "Year cannot be null")
    @Min(value = 1900, message = "Year must be greater than 1900")
    private Integer year;

    /**
     * The mileage of the car in kilometers.
     * Cannot be negative.
     */
    @NotNull(message = "Mileage cannot be null")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Double mileage;

    /**
     * The price of the car.
     * Must be greater than or equal to 0.
     */
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    /**
     * A description of the car.
     * Can be empty, but should not exceed 255 characters.
     */
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
}