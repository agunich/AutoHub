package com.alexgunich.repository;

import com.alexgunich.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с избранными автомобилями.
 * Использует Redis для кеширования.
 */
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    /**
     * Находит все автомобили в списке избранных для пользователя с кешированием в Redis.
     *
     * @param userId ID пользователя.
     * @return Список избранных автомобилей для данного пользователя.
     */
    @Cacheable(value = "favoritesByUser", key = "#userId")
    List<Favorite> findByUserId(Long userId);

    /**
     * Находит все записи о любимых автомобилях по ID автомобиля с кешированием в Redis.
     *
     * @param carId ID автомобиля.
     * @return Список избранных автомобилей по ID автомобиля.
     */
    @Cacheable(value = "favoritesByCar", key = "#carId")
    List<Favorite> findByCarId(Long carId);
}