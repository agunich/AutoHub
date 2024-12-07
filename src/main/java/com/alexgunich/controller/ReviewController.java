package com.alexgunich.controller;

import com.alexgunich.dto.ReviewDto;
import com.alexgunich.model.Review;
import com.alexgunich.service.ReviewService;
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
 * Controller for managing reviews.
 * Handles incoming HTTP requests and interacts with ReviewService.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewService reviewService;
    private final DtoConverter dtoConverter;

    @Autowired
    public ReviewController(ReviewService reviewService, DtoConverter dtoConverter) {
        this.reviewService = reviewService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Get all reviews.
     *
     * @return a list of review DTOs.
     */
    @GetMapping
    public List<ReviewDto> getAllReviews() {
        logger.info("Fetching all reviews");
        try {
            List<Review> reviews = reviewService.getAllReviews();
            List<ReviewDto> reviewDtos = reviews.stream()
                    .map(review -> dtoConverter.convertToDto(review, ReviewDto.class))
                    .collect(Collectors.toList());
            logger.debug("Found {} reviews", reviewDtos.size());
            return reviewDtos;
        } catch (Exception e) {
            logger.error("Error fetching reviews: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch reviews");
        }
    }

    /**
     * Get a review by its ID.
     *
     * @param id the ID of the review.
     * @return a review DTO or status 404 if not found.
     */
    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        logger.info("Fetching review with ID: {}", id);
        try {
            Review review = reviewService.getReviewById(id);
            ReviewDto reviewDto = dtoConverter.convertToDto(review, ReviewDto.class);
            logger.debug("Found review with ID: {}", id);
            return reviewDto;
        } catch (Exception e) {
            logger.error("Review with ID {} not found: {}", id, e.getMessage());
            throw new RuntimeException("Review not found with ID: " + id);
        }
    }

    /**
     * Create a new review.
     *
     * @param reviewDto the DTO of review data.
     * @return status 201 - object created.
     */
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody @Valid ReviewDto reviewDto) {
        logger.info("Creating a new review with data: {}", reviewDto);
        try {
            reviewService.createReview(dtoConverter.convertToEntity(reviewDto, Review.class));
            logger.debug("Review created successfully");
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Error creating review: {}", e.getMessage());
            throw new RuntimeException("Failed to create review");
        }
    }

    /**
     * Delete a review by its ID.
     *
     * @param id the ID of the review.
     * @return status 204 (No Content) if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        logger.info("Deleting review with ID: {}", id);
        try {
            reviewService.deleteReview(id);
            logger.debug("Review with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting review with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete review with ID: " + id);
        }
    }
}