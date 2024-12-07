package com.alexgunich.service;

import com.alexgunich.model.Review;
import com.alexgunich.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для работы с отзывами.
 */
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    /**
     * Получить все отзывы
     * @return список отзывов
     */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /**
     * Получить отзыв по ID
     * @param id идентификатор отзыва
     * @return сущность отзыва
     */
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    /**
     * Создать новый отзыв.
     *
     * @param review данные нового отзыва.
     * @return созданный отзыв.
     */
    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    /**
     * Удалить отзыв по ID.
     *
     * @param id идентификатор отзыва.
     */
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
