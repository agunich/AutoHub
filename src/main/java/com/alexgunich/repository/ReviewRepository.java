package com.alexgunich.repository;

import com.alexgunich.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с отзывами на автомобили.
 * Предоставляет методы для сохранения и поиска отзывов.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    /**
     * Находит все отзывы по ID автомобиля.
     *
     * @param carId ID автомобиля.
     * @return Список отзывов, связанных с данным автомобилем.
     */
    List<Review> findByCarId(Long carId);

    /**
     * Находит все отзывы по ID пользователя.
     *
     * @param userId ID пользователя.
     * @return Список отзывов, написанных данным пользователем.
     */
    List<Review> findByUserId(Long userId);
}
