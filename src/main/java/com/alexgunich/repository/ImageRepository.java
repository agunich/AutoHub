package com.alexgunich.repository;

import com.alexgunich.model.Image;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for working with car images.
 * Uses Redis for caching.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Finds all images associated with a car by its ID, with caching in Redis.
     *
     * @param carId the ID of the car.
     * @return a list of images associated with the given car.
     */
    @Cacheable(value = "imagesByCar", key = "#carId")
    List<Image> findByCarId(Long carId);
}