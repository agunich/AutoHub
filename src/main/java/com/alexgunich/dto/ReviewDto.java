package com.alexgunich.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO).
 * It contains details about the review made by a user for a specific car.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    /**
     * The ID of the user who created the review.
     * Should not be null.
     */
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    /**
     * The ID of the car that the review is associated with.
     * Should not be null.
     */
    @NotNull(message = "Car ID cannot be null")
    private Long carId;

    /**
     * The rating given by the user for the car. It should be a number between 1 and 5.
     * The value must be between 1 and 5.
     */
    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    /**
     * The comment written by the user in the review.
     * Provides additional feedback or opinion about the car.
     * Should not be empty.
     */
    @NotEmpty(message = "Comment cannot be empty")
    private String comment;

    /**
     * The timestamp when the review was created.
     * It represents the date and time of the review submission.
     * The date must not be in the future.
     */
    @NotNull(message = "Creation date cannot be null")
    @PastOrPresent(message = "Creation date cannot be in the future")
    private LocalDateTime createdAt;
}