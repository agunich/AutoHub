package com.alexgunich.controller;

import com.alexgunich.dto.ReviewDto;
import com.alexgunich.model.Review;
import com.alexgunich.service.ReviewService;
import com.alexgunich.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с отзывами.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final DtoConverter dtoConverter;

    @Autowired
    public ReviewController(ReviewService reviewService, DtoConverter dtoConverter) {
        this.reviewService = reviewService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Получить все отзывы
     * @return DTO список отзывов
     */
    @GetMapping
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return reviews.stream()
                .map(review -> dtoConverter.convertToDto(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить отзыв по ID
     * @param id идентификатор отзыва
     * @return DTO отзыва
     */
    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return dtoConverter.convertToDto(reviewService.getReviewById(id), ReviewDto.class);
    }

    /**
     * Создать новый отзыв.
     *
     * @param review данные нового отзыва.
     * @return status 201 - объект создан.
     */
    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody Review review) {
        reviewService.createReview(review);
        return ResponseEntity.status(201).build();
    }

    /**
     * Удалить отзыв.
     *
     * @param id идентификатор отзыва.
     * @return статус 204 (No Content), если удаление выполнено.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
