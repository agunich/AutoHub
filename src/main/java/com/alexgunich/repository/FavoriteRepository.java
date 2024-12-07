package com.alexgunich.repository;

import com.alexgunich.model.Favorite;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for working with favorite cars.
 * Uses Redis for caching.
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Finds all favorite cars for a user, with caching in Redis.
     *
     * @param userId the ID of the user.
     * @return a list of favorite cars for the given user.
     */
    @Cacheable(value = "favoritesByUser", key = "#userId")
    List<Favorite> findByUserId(Long userId);

    /**
     * Finds all favorite records for a car, with caching in Redis.
     *
     * @param carId the ID of the car.
     * @return a list of favorite cars for the given car ID.
     */
    @Cacheable(value = "favoritesByCar", key = "#carId")
    List<Favorite> findByCarId(Long carId);
}