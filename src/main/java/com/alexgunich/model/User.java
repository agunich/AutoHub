package com.alexgunich.model;

import com.alexgunich.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;


/**
 * Модель пользователя системы.
 * Кеширование информации о пользователе, его автомобилях и избранных автомобилях.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Электронная почта пользователя. Должна быть уникальной.
     */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Роль пользователя, которая определяет его права в системе.
     */
    @Column(name = "role", nullable = false)
    private Role role;

    /**
     * Список автомобилей, принадлежащих пользователю.
     */
    @OneToMany(mappedBy = "user")
    private List<Car> cars;

    /**
     * Список избранных автомобилей пользователя.
     */
    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;

    /**
     * Список отзывов, оставленных пользователем.
     */
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}