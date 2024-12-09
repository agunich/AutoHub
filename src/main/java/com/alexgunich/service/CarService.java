package com.alexgunich.service;

import com.alexgunich.model.Car;
import com.alexgunich.repository.CarRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing cars.
 * Contains business logic and interacts with the CarRepository.
 */
@Service
public class CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Retrieve all cars with caching enabled.
     *
     * @return a list of cars.
     */
    @Cacheable(value = "cars", key = "'allCars'")
    public List<Car> getAllCars() {
        logger.info("Fetching all cars from the database");
        try {
            List<Car> cars = carRepository.findAll();
            logger.debug("Successfully fetched {} cars from the database", cars.size());
            return cars;
        } catch (Exception e) {
            String errorMessage = "Failed to fetch cars from the database.";
            logger.error("{} Cause: {}", errorMessage, e.getMessage(), e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Retrieve a car by its ID with caching enabled.
     *
     * @param id the ID of the car.
     * @return the car entity.
     */
    @Cacheable(value = "cars", key = "#id")
    public Car getCarById(Long id) {
        logger.info("Fetching car with ID {}", id);
        try {
            return carRepository.findById(id)
                    .orElseThrow(() -> {
                        String notFoundMessage = "Car with ID " + id + " was not found.";
                        logger.warn(notFoundMessage);
                        return new EntityNotFoundException(notFoundMessage);
                    });
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            String errorMessage = "An error occurred while fetching car with ID " + id;
            logger.error("{} Cause: {}", errorMessage, e.getMessage(), e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Create a new car.
     *
     * @param car the data of the new car.
     * @return the created car.
     */
    @CachePut(value = "cars", key = "#result.id")
    public Car createCar(Car car) {
        logger.info("Creating a new car");
        try {
            Car savedCar = carRepository.save(car);
            logger.debug("Successfully created a car with ID {}", savedCar.getId());
            return savedCar;
        } catch (Exception e) {
            String errorMessage = "Failed to create a new car.";
            logger.error("{} Cause: {}", errorMessage, e.getMessage(), e);
            throw new RuntimeException(errorMessage, e);
        }
    }

    /**
     * Delete a car by its ID.
     *
     * @param id the ID of the car to delete.
     */
    public void deleteCar(Long id) {
        logger.info("Deleting car with ID {}", id);
        try {
            if (!carRepository.existsById(id)) {
                String notFoundMessage = "Car with ID " + id + " does not exist.";
                logger.warn(notFoundMessage);
                throw new EntityNotFoundException(notFoundMessage);
            }

            carRepository.deleteById(id);
            logger.debug("Successfully deleted car with ID {}", id);
        } catch (EntityNotFoundException e) {
            throw e;
        } catch (Exception e) {
            String errorMessage = "An error occurred while deleting car with ID " + id;
            logger.error("{} Cause: {}", errorMessage, e.getMessage(), e);
            throw new RuntimeException(errorMessage, e);
        }
    }
}