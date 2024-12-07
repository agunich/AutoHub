package com.alexgunich.controller;

import com.alexgunich.dto.UserDto;
import com.alexgunich.model.User;
import com.alexgunich.service.UserService;
import com.alexgunich.util.DtoConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing users.
 * Handles incoming HTTP requests and interacts with UserService.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final DtoConverter dtoConverter;

    @Autowired
    public UserController(UserService userService, DtoConverter dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Get all users.
     *
     * @return a list of user DTOs.
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        logger.info("Fetching all users");
        try {
            List<UserDto> userDtos = userService.getAllUsers().stream()
                    .map(user -> dtoConverter.convertToDto(user, UserDto.class))
                    .collect(Collectors.toList());
            logger.debug("Found {} users", userDtos.size());
            return userDtos;
        } catch (Exception e) {
            logger.error("Error fetching users: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch users");
        }
    }

    /**
     * Get a user by ID.
     *
     * @param id the ID of the user.
     * @return a user DTO or status 404 if not found.
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        logger.info("Fetching user with ID: {}", id);
        try {
            User user = userService.getUserById(id);
            UserDto userDto = dtoConverter.convertToDto(user, UserDto.class);
            logger.debug("Found user with ID: {}", id);
            return userDto;
        } catch (Exception e) {
            logger.error("User with ID {} not found: {}", id, e.getMessage());
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    /**
     * Create a new user.
     *
     * @param userDto the data for the new user.
     * @return the created user.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDto userDto) {
        logger.info("Creating a new user with data: {}", userDto);
        try {
            User user = userService.createUser(dtoConverter.convertToEntity(userDto, User.class));
            logger.debug("User created with ID: {}", user.getId());
            return ResponseEntity.status(201).body(user);
        } catch (Exception e) {
            logger.error("Error creating user: {}", e.getMessage());
            throw new RuntimeException("Failed to create user");
        }
    }

    /**
     * Delete a user by ID.
     *
     * @param id the ID of the user.
     * @return status 204 (No Content) if the user is successfully deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            userService.deleteUser(id);
            logger.debug("User with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete user with ID: " + id);
        }
    }
}