package com.alexgunich.repository;

import com.alexgunich.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с автомобилями.
 * Использует JPA для стандартных операций.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Находит автомобили по марке.
     *
     * @param brand Марка автомобиля.
     * @return Список автомобилей с заданной маркой.
     */
    List<Car> findByBrand(String brand);

    /**
     * Находит автомобили по модели.
     *
     * @param model Модель автомобиля.
     * @return Список автомобилей с заданной моделью.
     */
    List<Car> findByModel(String model);

    /**
     * Находит автомобили по году выпуска.
     *
     * @param year Год выпуска автомобиля.
     * @return Список автомобилей с заданным годом выпуска.
     */
    List<Car> findByYear(int year);

    /**
     * Находит автомобили в пределах заданного диапазона цены.
     *
     * @param minPrice Минимальная цена.
     * @param maxPrice Максимальная цена.
     * @return Список автомобилей в пределах диапазона цен.
     */
    List<Car> findByPriceBetween(double minPrice, double maxPrice);

    /**
     * Находит автомобили в пределах заданного диапазона пробега.
     *
     * @param minMileage Минимальный пробег.
     * @param maxMileage Максимальный пробег.
     * @return Список автомобилей в пределах диапазона пробега.
     */
    List<Car> findByMileageBetween(double minMileage, double maxMileage);

    /**
     * Находит автомобиль по его ID.
     *
     * @param id ID автомобиля.
     * @return Optional<Car> С Optional, так как автомобиль может не существовать.
     */
    Optional<Car> findById(Long id);
}