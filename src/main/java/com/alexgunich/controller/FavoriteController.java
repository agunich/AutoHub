package com.alexgunich.controller;

import com.alexgunich.dto.FavoriteDto;
import com.alexgunich.model.Favorite;
import com.alexgunich.service.FavoriteService;
import com.alexgunich.util.DtoConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing favorites.
 * Handles incoming HTTP requests and interacts with FavoriteService.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    private final FavoriteService favoriteService;
    private final DtoConverter dtoConverter;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, DtoConverter dtoConverter) {
        this.favoriteService = favoriteService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Get the list of all favorite entries.
     *
     * @return a list of favorite DTOs.
     */
    @GetMapping
    public List<FavoriteDto> getAllFavorites() {
        logger.info("Fetching all favorite records");
        try {
            List<Favorite> favorites = favoriteService.getAllFavorites();
            List<FavoriteDto> favoriteDtos = favorites.stream()
                    .map(favorite -> dtoConverter.convertToDto(favorite, FavoriteDto.class))
                    .collect(Collectors.toList());
            logger.debug("Found {} favorite records", favoriteDtos.size());
            return favoriteDtos;
        } catch (Exception e) {
            logger.error("Error fetching favorite records: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch favorites");
        }
    }

    /**
     * Get a favorite entry by ID.
     *
     * @param id the ID of the favorite record.
     * @return a favorite DTO or status 404 if not found.
     */
    @GetMapping("/{id}")
    public FavoriteDto getFavoriteById(@PathVariable Long id) {
        logger.info("Fetching favorite record with ID: {}", id);
        try {
            Favorite favorite = favoriteService.getFavoriteById(id);
            FavoriteDto favoriteDto = dtoConverter.convertToDto(favorite, FavoriteDto.class);
            logger.debug("Found favorite record with ID: {}", id);
            return favoriteDto;
        } catch (Exception e) {
            logger.error("Favorite record with ID {} not found: {}", id, e.getMessage());
            throw new RuntimeException("Favorite not found with ID: " + id);
        }
    }

    /**
     * Add a new favorite entry.
     *
     * @param favoriteDto the favorite data.
     * @return status 201 - object created.
     */
    @PostMapping
    public ResponseEntity<Void> createFavorite(@RequestBody FavoriteDto favoriteDto) {
        logger.info("Creating a new favorite record with data: {}", favoriteDto);
        try {
            favoriteService.createFavorite(dtoConverter.convertToEntity(favoriteDto, Favorite.class));
            logger.debug("Favorite record created successfully");
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Error creating favorite record: {}", e.getMessage());
            throw new RuntimeException("Failed to create favorite");
        }
    }

    /**
     * Delete a favorite entry by ID.
     *
     * @param id the ID of the favorite record.
     * @return status 204 (No Content) if the favorite record is successfully deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        logger.info("Deleting favorite record with ID: {}", id);
        try {
            favoriteService.deleteFavorite(id);
            logger.debug("Favorite record with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting favorite record with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete favorite with ID: " + id);
        }
    }
}