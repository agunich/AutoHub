package com.alexgunich.service;

import com.alexgunich.model.Favorite;
import com.alexgunich.repository.FavoriteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing favorite cars.
 * This class provides methods to create, get, and delete favorite records.
 */
@Service
public class FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteService.class);

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Get all favorite records with caching.
     * @return list of favorite records.
     */
    @Cacheable(value = "favorites", key = "'allFavorites'")
    public List<Favorite> getAllFavorites() {
        logger.info("Fetching all favorite records from the database");
        try {
            List<Favorite> favorites = favoriteRepository.findAll();
            logger.debug("Found {} favorite records", favorites.size());
            return favorites;
        } catch (Exception e) {
            logger.error("Error while fetching all favorite records: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch favorite records");
        }
    }

    /**
     * Get a favorite record by ID with caching.
     * @param id the ID of the favorite record.
     * @return the favorite record entity.
     */
    @Cacheable(value = "favorites", key = "#id")
    public Favorite getFavoriteById(Long id) {
        logger.info("Fetching favorite record with ID: {}", id);
        try {
            return favoriteRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Favorite record with ID {} not found", id);
                        return new EntityNotFoundException("Favorite record with ID " + id + " not found");
                    });
        } catch (EntityNotFoundException e) {
            logger.warn("Favorite record not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching favorite record with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch favorite record with ID: " + id);
        }
    }

    /**
     * Create a new favorite record.
     * @param favorite the data of the new favorite record.
     * @return the created favorite record.
     */
    @CachePut(value = "favorites", key = "#result.id")
    @CacheEvict(value = "favorites", key = "'allFavorites'")
    public Favorite createFavorite(Favorite favorite) {
        logger.info("Creating a new favorite record: {}", favorite);
        try {
            Favorite savedFavorite = favoriteRepository.save(favorite);
            logger.debug("Favorite record created with ID: {}", savedFavorite.getId());
            return savedFavorite;
        } catch (Exception e) {
            logger.error("Error while creating favorite record: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create favorite record");
        }
    }

    /**
     * Delete a favorite record by ID and update the cache.
     * @param id the ID of the favorite record to be deleted.
     */
    @CacheEvict(value = "favorites", key = "#id")  // Cache eviction for the specific record
    public void deleteFavorite(Long id) {
        logger.info("Deleting favorite record with ID: {}", id);
        try {
            if (!favoriteRepository.existsById(id)) {
                logger.warn("Favorite record with ID {} not found for deletion", id);
                throw new EntityNotFoundException("Favorite record with ID " + id + " not found");
            }
            favoriteRepository.deleteById(id);
            logger.debug("Favorite record with ID {} has been deleted", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Cannot delete favorite record: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting favorite record with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete favorite record with ID: " + id);
        }
    }
}