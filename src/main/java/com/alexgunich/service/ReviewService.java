package com.alexgunich.service;

import com.alexgunich.model.Review;
import com.alexgunich.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing reviews.
 */
@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Retrieves all reviews.
     *
     * @return a list of all reviews.
     */
    public List<Review> getAllReviews() {
        logger.info("Fetching all reviews from the database");
        try {
            List<Review> reviews = reviewRepository.findAll();
            logger.debug("Found {} reviews", reviews.size());
            return reviews;
        } catch (Exception e) {
            logger.error("Error while fetching all reviews: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch reviews");
        }
    }

    /**
     * Retrieves a review by its ID.
     *
     * @param id the ID of the review.
     * @return the review entity.
     */
    public Review getReviewById(Long id) {
        logger.info("Fetching review with ID: {}", id);
        try {
            return reviewRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Review with ID {} not found", id);
                        return new EntityNotFoundException("Review with ID " + id + " not found");
                    });
        } catch (EntityNotFoundException e) {
            logger.warn("Review not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while fetching review with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch review with ID: " + id);
        }
    }

    /**
     * Creates a new review.
     *
     * @param review the review data to create.
     * @return the created review entity.
     */
    public Review createReview(Review review) {
        logger.info("Creating a new review: {}", review);
        try {
            Review savedReview = reviewRepository.save(review);
            logger.debug("Review created with ID: {}", savedReview.getId());
            return savedReview;
        } catch (Exception e) {
            logger.error("Error while creating review: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create review");
        }
    }

    /**
     * Deletes a review by its ID.
     *
     * @param id the ID of the review to delete.
     */
    public void deleteReview(Long id) {
        logger.info("Deleting review with ID: {}", id);
        try {
            if (!reviewRepository.existsById(id)) {
                logger.error("Review with ID {} not found", id);
                throw new EntityNotFoundException("Review with ID " + id + " not found");
            }
            reviewRepository.deleteById(id);
            logger.debug("Review with ID {} has been deleted", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Cannot delete: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting review with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete review with ID: " + id);
        }
    }
}