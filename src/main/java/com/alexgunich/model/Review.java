package com.alexgunich.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Представляет отзыв, оставленный пользователем о конкретном автомобиле.
 * Отзыв включает в себя оценку (от 1 до 5), комментарий и временную метку, когда он был создан.
 * Каждый отзыв связан с пользователем и автомобилем.
 */
@Data
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь, который оставил отзыв.
     * Это связь "многие к одному", так как один пользователь может оставить множество отзывов.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Автомобиль, о котором написан отзыв.
     * Это связь "многие к одному", так как один автомобиль может иметь множество отзывов.
     */
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    /**
     * Оценка, поставленная автомобилю пользователем.
     * Оценка является целым числом от 1 до 5.
     */
    private Integer rating;

    /**
     * Комментарий, оставленный пользователем о автомобиле.
     * Комментарий предоставляет дополнительные детали о впечатлениях пользователя.
     */
    private String comment;

    /**
     * Временная метка, когда был создан отзыв.
     * Это поле автоматически заполняется, когда отзыв создается.
     */
    private LocalDateTime createdAt;
}
