package com.alexgunich.controller;

import com.alexgunich.dto.CarDto;
import com.alexgunich.model.Car;
import com.alexgunich.service.CarService;
import com.alexgunich.util.DtoConverter;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing cars.
 * Handles incoming HTTP requests and calls methods in CarService.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private static final Logger logger = LoggerFactory.getLogger(CarController.class);

    private final CarService carService;
    private final DtoConverter dtoConverter;

    @Autowired
    public CarController(CarService carService, DtoConverter dtoConverter) {
        this.carService = carService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Get all cars.
     *
     * @return list of car DTOs.
     */
    @GetMapping
    public List<CarDto> getAllCars() {
        logger.info("Fetching all cars");
        try {
            List<Car> cars = carService.getAllCars();
            List<CarDto> carDtos = cars.stream()
                    .map(car -> dtoConverter.convertToDto(car, CarDto.class))
                    .collect(Collectors.toList());
            logger.debug("Found {} cars", carDtos.size());
            return carDtos;
        } catch (Exception e) {
            logger.error("Error fetching cars: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch cars");
        }
    }

    /**
     * Get a car by ID.
     *
     * @param id the car ID.
     * @return car DTO.
     */
    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        logger.info("Fetching car with ID: {}", id);
        try {
            Car car = carService.getCarById(id);
            if (car == null) {
                throw new RuntimeException("Car not found with ID: " + id);
            }
            CarDto carDto = dtoConverter.convertToDto(car, CarDto.class);
            logger.debug("Found car with ID: {}", id);
            return carDto;
        } catch (RuntimeException e) {
            logger.error("Car with ID {} not found: {}", id, e.getMessage());
            throw e; // Let the exception be caught by @ControllerAdvice
        } catch (Exception e) {
            logger.error("Error fetching car with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to fetch car with ID: " + id);
        }
    }

    /**
     * Create a new car.
     *
     * @param carDto the car data.
     * @return status 201 - object created.
     */
    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody @Valid CarDto carDto) {
        logger.info("Creating a new car with data: {}", carDto);
        try {
            carService.createCar(dtoConverter.convertToEntity(carDto, Car.class));
            logger.debug("Car created successfully");
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            logger.error("Error creating car: {}", e.getMessage());
            throw new RuntimeException("Failed to create car");
        }
    }

    /**
     * Delete a car by ID.
     *
     * @param id the car ID.
     * @return status 204 (No Content) if car is successfully deleted.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        logger.info("Deleting car with ID: {}", id);
        try {
            carService.deleteCar(id);
            logger.debug("Car with ID {} deleted successfully", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting car with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete car with ID: " + id);
        }
    }
}