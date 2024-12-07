package com.alexgunich.repository;

import com.alexgunich.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for working with car reviews.
 * Provides methods for saving and retrieving reviews.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Finds all reviews associated with a car by its ID.
     *
     * @param carId the ID of the car.
     * @return a list of reviews associated with the given car.
     */
    List<Review> findByCarId(Long carId);

    /**
     * Finds all reviews written by a user by their ID.
     *
     * @param userId the ID of the user.
     * @return a list of reviews written by the given user.
     */
    List<Review> findByUserId(Long userId);
}