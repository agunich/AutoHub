package com.alexgunich.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель избранных автомобилей.
 * Кешируем список избранных автомобилей пользователя.
 */
@Data
@Entity
@Table(name = "favorites")
public class Favorite {

    /**
     * Уникальный идентификатор записи в списке избранных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователь, который добавил автомобиль в избранное.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Автомобиль, добавленный в избранное.
     */
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}
