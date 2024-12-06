package com.alexgunich.repository;

import com.alexgunich.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с изображениями автомобилей.
 * Использует Redis для кеширования.
 */
@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    /**
     * Находит все изображения по ID автомобиля с кешированием в Redis.
     *
     * @param carId ID автомобиля.
     * @return Список изображений, связанных с данным автомобилем.
     */
    @Cacheable(value = "imagesByCar", key = "#carId")
    List<Image> findByCarId(Long carId);
}