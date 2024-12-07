package com.alexgunich.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO).
 * It contains user details such as email, name, role, and the list of cars associated with the user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * The email of the user.
     * It serves as the unique identifier for the user in the system.
     * Should be a valid email format.
     */
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email must be a valid email address")
    private String email;

    /**
     * The name of the user.
     * This field stores the user's full name.
     * Should not be empty and should be between 1 and 100 characters.
     */
    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    /**
     * The role of the user within the system.
     * It defines the access level or permissions for the user (e.g., ADMIN, USER).
     * Should not be empty.
     */
    @NotNull(message = "Role cannot be null")
    @NotEmpty(message = "Role cannot be empty")
    private String role;
}