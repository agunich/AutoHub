package com.alexgunich.service;

import com.alexgunich.model.Car;
import com.alexgunich.model.Image;
import com.alexgunich.repository.CarRepository;
import com.alexgunich.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервисный класс для работы с изображениями.
 */
@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final CarRepository carRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, CarRepository carRepository) {
        this.imageRepository = imageRepository;
        this.carRepository = carRepository;
    }

    /**
     * Добавление изображения к автомобилю с кешированием
     *
     * @param carId    идентификатор автомобиля
     * @param imageUrl URL изображения
     */
    @CachePut(value = "images", key = "#carId")
    public void addImageToCar(Long carId, String imageUrl) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Автомобиль с ID " + carId + " не найден"));
        Image image = new Image();
        image.setCar(car);
        image.setImageUrl(imageUrl);
        imageRepository.save(image);
    }

    /**
     * Получение списка URL изображений по идентификатору автомобиля с кешированием
     *
     * @param carId идентификатор автомобиля
     * @return список URL изображений
     */
    @Cacheable(value = "images", key = "#carId")
    public List<String> getImagesByCarId(Long carId) {
        return imageRepository.findByCarId(carId)
                .stream()
                .map(Image::getImageUrl)
                .toList();
    }

    /**
     * Удаление изображения по его ID и обновление кеша
     *
     * @param imageId идентификатор изображения
     */
    public void deleteImage(Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new EntityNotFoundException("Изображение с ID " + imageId + " не найдено");
        }
        imageRepository.deleteById(imageId);
    }
}