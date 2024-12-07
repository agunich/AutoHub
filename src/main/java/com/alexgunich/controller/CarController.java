package com.alexgunich.controller;

import com.alexgunich.dto.CarDto;
import com.alexgunich.model.Car;
import com.alexgunich.service.CarService;
import com.alexgunich.util.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с автомобилями.
 * Обрабатывает входящие HTTP-запросы и вызывает методы CarService.
 */
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
    private final DtoConverter dtoConverter;

    @Autowired
    public CarController(CarService carService, DtoConverter dtoConverter) {
        this.carService = carService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Получить все автомобили.
     *
     * @return список DTO автомобилей.
     */
    @GetMapping
    public List<CarDto> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return cars.stream()
                .map(car -> dtoConverter.convertToDto(car, CarDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить автомобиль по ID.
     *
     * @param id идентификатор автомобиля.
     * @return DTO автомобиля.
     */
    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return dtoConverter.convertToDto(
                carService.getCarById(id), CarDto.class
        );
    }

    /**
     * Создать новый автомобиль.
     *
     * @param carDto данные нового автомобиля.
     * @return status 201 - объект создан.
     */
    @PostMapping
    public ResponseEntity<Void> createCar(@RequestBody @Valid CarDto carDto) {
        carService.createCar(dtoConverter.convertToEntity(carDto, Car.class));
        return ResponseEntity.status(201).build();
    }

    /**
     * Удалить автомобиль по ID.
     *
     * @param id идентификатор автомобиля.
     * @return статус 204 (No Content), если автомобиль успешно удален.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}