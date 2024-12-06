package com.alexgunich.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель изображения для объявления.
 * Кешируем только URL изображения.
 */
@Data
@Entity
@Table(name = "images")
public class Image {

    /**
     * Уникальный идентификатор изображения.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * URL изображения.
     */
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    /**
     * Автомобиль, к которому привязано изображение.
     */
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;
}