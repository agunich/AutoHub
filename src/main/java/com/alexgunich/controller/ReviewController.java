package com.alexgunich.controller;

import com.alexgunich.dto.ReviewDto;
import com.alexgunich.model.Review;
import com.alexgunich.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с отзывами.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    /**
     * Получить все отзывы
     * @return список DTO отзывов
     */
    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /**
     * Получить отзыв по ID
     * @param id идентификатор отзыва
     * @return DTO отзыва
     */
    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id);
    }

    /**
     * Создать новый отзыв.
     *
     * @param review данные нового отзыва.
     * @return созданный отзыв.
     */
    @PostMapping
    public Review createReview(@RequestBody Review review) {
        return reviewService.createReview(review);
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
