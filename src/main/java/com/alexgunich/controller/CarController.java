package com.alexgunich.controller;

import com.alexgunich.dto.CarDto;
import com.alexgunich.model.Car;
//import com.alexgunich.search.CarElasticsearch;
//import com.alexgunich.search.CarSearchService;
import com.alexgunich.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с автомобилями.
 * Обрабатывает входящие HTTP-запросы и вызывает методы CarService.
 */
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;
//    private final CarSearchService carSearchService;

    @Autowired
    public CarController(CarService carService) { // <- insert CarSearchService carSearchService
        this.carService = carService;
//        this.carSearchService = carSearchService;
    }

    /**
     * Получить все автомобили.
     *
     * @return список DTO автомобилей.
     */
    @GetMapping
    public List<CarDto> getAllCars() {
        return carService.getAllCars();
    }

    /**
     * Получить автомобиль по ID.
     *
     * @param id идентификатор автомобиля.
     * @return DTO автомобиля.
     */
    @GetMapping("/{id}")
    public CarDto getCarById(@PathVariable Long id) {
        return carService.getCarById(id);
    }

    /**
     * Создать новый автомобиль.
     *
     * @param car данные нового автомобиля.
     * @return созданный автомобиль.
     */
    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.createCar(car);
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

//    /**
//     * Эндпоинт для поиска автомобилей с динамическими фильтрами.
//     *
//     * @param brand      марка автомобиля (необязательно).
//     * @param model      модель автомобиля (необязательно).
//     * @param minYear    минимальный год выпуска (необязательно).
//     * @param maxYear    максимальный год выпуска (необязательно).
//     * @param minPrice   минимальная цена (необязательно).
//     * @param maxPrice   максимальная цена (необязательно).
//     * @param minMileage минимальный пробег (необязательно).
//     * @param maxMileage максимальный пробег (необязательно).
//     * @return список найденных автомобилей.
//     */
//    @GetMapping
//    public List<CarElasticsearch> searchCars(
//            @RequestParam(required = false) String brand,
//            @RequestParam(required = false) String model,
//            @RequestParam(required = false) Integer minYear,
//            @RequestParam(required = false) Integer maxYear,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice,
//            @RequestParam(required = false) Double minMileage,
//            @RequestParam(required = false) Double maxMileage
//    ) {
//        return carSearchService.searchCars(brand, model, minYear, maxYear, minPrice, maxPrice, minMileage, maxMileage);
//    }
}