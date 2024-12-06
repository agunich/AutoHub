package com.alexgunich.service;

import com.alexgunich.dto.ReviewDto;
import com.alexgunich.model.Review;
import com.alexgunich.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @return список DTO отзывов
     */
    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
                .map(this::convertToDto) // Преобразуем сущности в DTO
                .collect(Collectors.toList());
    }

    /**
     * Получить отзыв по ID
     * @param id идентификатор отзыва
     * @return DTO отзыва
     */
    public ReviewDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return convertToDto(review);
    }

    /**
     * Преобразовать сущность отзыва в DTO
     * @param review сущность отзыва
     * @return DTO отзыва
     */
    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getUser().getId(),
                review.getCar().getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
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
