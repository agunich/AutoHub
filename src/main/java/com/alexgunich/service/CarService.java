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
            logger.debug("Found {} cars", cars.size());
            return cars;
        } catch (Exception e) {
            logger.error("Error fetching all cars: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all cars", e);
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
        logger.info("Fetching car with ID: {}", id);
        try {
            return carRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.error("Car with ID {} not found", id);
                        return new EntityNotFoundException("Car with ID " + id + " not found");
                    });
        } catch (EntityNotFoundException e) {
            logger.warn("Car with ID {} not found: {}", id, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching car with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch car with ID: " + id, e);
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
        logger.info("Creating a new car: {}", car);
        try {
            Car savedCar = carRepository.save(car);
            logger.debug("Car created with ID: {}", savedCar.getId());
            return savedCar;
        } catch (Exception e) {
            logger.error("Error creating a new car: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create a new car", e);
        }
    }

    /**
     * Delete a car by its ID and update the cache.
     *
     * @param id the ID of the car to delete.
     */
    public void deleteCar(Long id) {
        logger.info("Deleting car with ID: {}", id);
        try {
            if (!carRepository.existsById(id)) {
                logger.error("Car with ID {} not found", id);
                throw new EntityNotFoundException("Car with ID " + id + " not found");
            }

            carRepository.deleteById(id);
            logger.debug("Car with ID {} has been deleted", id);
        } catch (EntityNotFoundException e) {
            logger.warn("Cannot delete car: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting car with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete car with ID: " + id, e);
        }
    }
}