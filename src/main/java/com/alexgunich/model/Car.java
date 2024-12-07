package com.alexgunich.model;

import com.alexgunich.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

/**
 * Модель для представления автомобиля.
 * Кеширование информации о марке, модели, цене и описании.
 * Статус автомобиля не кешируем.
 */
@Data
@Entity
@Table(name = "cars")
public class Car {

    /**
     * Уникальный идентификатор автомобиля.
     * Генерируется автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Владелец автомобиля (пользователь).
     * Связь с сущностью User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Марка автомобиля.
     */
    private String brand;

    /**
     * Модель автомобиля.
     */
    private String model;

    /**
     * Год выпуска автомобиля.
     */
    private Integer year;

    /**
     * Пробег автомобиля в километрах.
     */
    private Double mileage;

    /**
     * Цена автомобиля.
     */
    private Double price;

    /**
     * Описание автомобиля, которое может включать дополнительную информацию.
     */
    private String description;

    /**
     * Статус автомобиля.
     * Значение из перечисления Status.
     */

    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * Дата и время создания записи об автомобиле.
     */
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи об автомобиле.
     */
    private LocalDateTime updatedAt;

    /**
     * Список изображений, связанных с автомобилем.
     * Один автомобиль может иметь несколько изображений.
     */
    @OneToMany(mappedBy = "car")
    private List<Image> images;

    /**
     * Список отзывов, связанных с автомобилем.
     * Один автомобиль может иметь несколько отзывов.
     */
    @OneToMany(mappedBy = "car")
    private List<Review> reviews;

    /**
     * Список автомобилей в избранном у пользователей.
     * Один автомобиль может быть добавлен в избранное несколькими пользователями.
     */
    @OneToMany(mappedBy = "car")
    private List<Favorite> favorites;
}