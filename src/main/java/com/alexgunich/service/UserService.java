package com.alexgunich.service;

import com.alexgunich.model.User;
import com.alexgunich.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 * Contains business logic and interacts with the UserRepository.
 */
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username (for authentication).
     *
     * @param username the username (email) of the user
     * @return UserDetails object containing user information
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by email: {}", username);
        try {
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> {
                        logger.warn("User with email {} not found", username);
                        return new UsernameNotFoundException("User not found with email: " + username);
                    });
            logger.debug("User found: {}", user);
            return org.springframework.security.core.userdetails.User
                    .builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().name())
                    .build();
        } catch (Exception e) {
            logger.error("Error while loading user by email: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Retrieves all users.
     *
     * @return a list of users
     */
    public List<User> getAllUsers() {
        logger.info("Fetching all users from the database");
        try {
            List<User> users = userRepository.findAll();
            logger.debug("Found {} users", users.size());
            return users;
        } catch (Exception e) {
            logger.error("Error while fetching all users: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch users");
        }
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user
     * @return the user entity
     */
    public User getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        try {
            return userRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("User with ID {} not found", id);
                        return new EntityNotFoundException("User with ID " + id + " not found");
                    });
        } catch (EntityNotFoundException e) {
            logger.warn("User not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch user with ID: " + id);
        }
    }

    /**
     * Creates a new user.
     *
     * @param user the user data
     * @return the created user
     */
    public User createUser(User user) {
        logger.info("Creating a new user: {}", user);
        try {
            User savedUser = userRepository.save(user);
            logger.debug("User created with ID: {}", savedUser.getId());
            return savedUser;
        } catch (Exception e) {
            logger.error("Error while creating user: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create user");
        }
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user
     */
    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        try {
            if (!userRepository.existsById(id)) {
                logger.warn("User with ID {} not found for deletion", id);
                throw new EntityNotFoundException("User with ID " + id + " not found");
            }
            userRepository.deleteById(id);
            logger.debug("User with ID {} has been deleted", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Cannot delete: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete user with ID: " + id);
        }
    }
}