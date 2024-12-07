package com.alexgunich.service;

import com.alexgunich.model.Car;
import com.alexgunich.model.Image;
import com.alexgunich.repository.CarRepository;
import com.alexgunich.repository.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing images.
 */
@Service
public class ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;
    private final CarRepository carRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository, CarRepository carRepository) {
        this.imageRepository = imageRepository;
        this.carRepository = carRepository;
    }

    /**
     * Adds an image to a car with caching.
     *
     * @param carId    the ID of the car to which the image will be added.
     * @param imageUrl the URL of the image to add.
     */
    @CachePut(value = "images", key = "#carId")
    public void addImageToCar(Long carId, String imageUrl) {
        logger.info("Adding image to car with ID: {}, Image URL: {}", carId, imageUrl);
        try {
            Car car = carRepository.findById(carId)
                    .orElseThrow(() -> {
                        logger.error("Car with ID {} not found", carId);
                        return new EntityNotFoundException("Car with ID " + carId + " not found");
                    });

            Image image = new Image();
            image.setCar(car);
            image.setImageUrl(imageUrl);

            imageRepository.save(image);
            logger.debug("Image added to car with ID: {}, Image URL: {}", carId, imageUrl);
        } catch (Exception e) {
            logger.error("Error while adding image to car with ID {}: {}", carId, e.getMessage(), e);
            throw new RuntimeException("Failed to add image to car with ID: " + carId, e);
        }
    }

    /**
     * Retrieves a list of image URLs by car ID with caching.
     *
     * @param carId the ID of the car whose images will be retrieved.
     * @return a list of image URLs.
     */
    @Cacheable(value = "images", key = "#carId")
    public List<String> getImagesByCarId(Long carId) {
        logger.info("Fetching images for car with ID: {}", carId);
        try {
            List<String> images = imageRepository.findByCarId(carId)
                    .stream()
                    .map(Image::getImageUrl)
                    .toList();

            logger.debug("Found {} images for car with ID: {}", images.size(), carId);
            return images;
        } catch (Exception e) {
            logger.error("Error while fetching images for car with ID {}: {}", carId, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch images for car with ID: " + carId, e);
        }
    }

    /**
     * Deletes an image by its ID and updates the cache.
     *
     * @param imageId the ID of the image to delete.
     */
    public void deleteImage(Long imageId) {
        logger.info("Deleting image with ID: {}", imageId);
        try {
            if (!imageRepository.existsById(imageId)) {
                logger.error("Image with ID {} not found", imageId);
                throw new EntityNotFoundException("Image with ID " + imageId + " not found");
            }

            imageRepository.deleteById(imageId);
            logger.debug("Image with ID {} has been deleted", imageId);
        } catch (EntityNotFoundException e) {
            logger.warn("Cannot delete image: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting image with ID {}: {}", imageId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete image with ID: " + imageId, e);
        }
    }
}