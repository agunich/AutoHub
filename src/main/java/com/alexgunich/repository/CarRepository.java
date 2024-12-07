package com.alexgunich.repository;

import com.alexgunich.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for working with cars.
 * Uses JPA for standard operations such as CRUD.
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Finds cars by brand.
     *
     * @param brand the brand of the car.
     * @return a list of cars with the given brand.
     */
    List<Car> findByBrand(String brand);

    /**
     * Finds cars by model.
     *
     * @param model the model of the car.
     * @return a list of cars with the given model.
     */
    List<Car> findByModel(String model);

    /**
     * Finds cars by manufacturing year.
     *
     * @param year the year of manufacture.
     * @return a list of cars with the given year of manufacture.
     */
    List<Car> findByYear(int year);

    /**
     * Finds cars within a given price range.
     *
     * @param minPrice the minimum price.
     * @param maxPrice the maximum price.
     * @return a list of cars within the given price range.
     */
    List<Car> findByPriceBetween(double minPrice, double maxPrice);

    /**
     * Finds cars within a given mileage range.
     *
     * @param minMileage the minimum mileage.
     * @param maxMileage the maximum mileage.
     * @return a list of cars within the given mileage range.
     */
    List<Car> findByMileageBetween(double minMileage, double maxMileage);

    /**
     * Finds a car by its ID.
     *
     * @param id the ID of the car.
     * @return an Optional containing the car, or an empty Optional if not found.
     */
    Optional<Car> findById(Long id);
}