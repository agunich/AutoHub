package com.alexgunich.service;

import com.alexgunich.dto.CarDto;
import com.alexgunich.model.Car;
import com.alexgunich.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для работы с автомобилями.
 * Содержит бизнес-логику и взаимодействует с репозиторием CarRepository.
 */
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Получить все автомобили с кешированием в Redis.
     *
     * @return список автомобилей.
     */
    @Cacheable(value = "cars", key = "'allCars'")
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    /**
     * Получить автомобиль по ID с кешированием.
     *
     * @param id идентификатор автомобиля.
     * @return сущность автомобиля.
     */
    @Cacheable(value = "cars", key = "#id")
    public Car getCarById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));
    }

    /**
     * Создать новый автомобиль.
     *
     * @param car данные нового автомобиля.
     * @return созданный автомобиль.
     */
    @CachePut(value = "cars", key = "#car.id")
    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    /**
     * Удалить автомобиль по ID и обновить кеш.
     *
     * @param id идентификатор автомобиля.
     */
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }
}