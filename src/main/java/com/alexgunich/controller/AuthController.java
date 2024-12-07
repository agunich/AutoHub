package com.alexgunich.controller;

import com.alexgunich.model.User;
import com.alexgunich.service.UserService;
import com.alexgunich.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user registration and login.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserService userService, JwtUtil jwtUtil, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Register a new user.
     *
     * @param user the data of the new user
     * @return ResponseEntity with the result of the registration
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            // Encrypt password before saving it
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            // Check if user with the same email already exists
            if (userService.getAllUsers().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
                logger.warn("Attempted registration with existing email: {}", user.getEmail());
                return ResponseEntity.status(400).body("A user with this email already exists");
            }

            userService.createUser(user);
            logger.info("User successfully registered with email: {}", user.getEmail());
            return ResponseEntity.ok("User registered successfully");

        } catch (Exception e) {
            logger.error("Error occurred during user registration: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred during registration");
        }
    }

    /**
     * User login and JWT token generation.
     *
     * @param user the user data
     * @return ResponseEntity with JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        try {
            // Authenticate user by loading user details
            User authenticatedUser = (User) userService.loadUserByUsername(user.getEmail());

            if (authenticatedUser != null) {
                String token = jwtUtil.generateToken(user.getEmail());
                logger.info("User successfully logged in with email: {}", user.getEmail());
                return ResponseEntity.ok("Bearer " + token);
            }

            logger.warn("Failed login attempt for email: {}", user.getEmail());
            return ResponseEntity.status(401).body("Invalid credentials");

        } catch (Exception e) {
            logger.error("Error occurred during login for email {}: {}", user.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred during login");
        }
    }
}