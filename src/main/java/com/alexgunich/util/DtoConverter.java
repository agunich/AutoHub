package com.alexgunich.util;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Utility class for converting between entities and DTOs using ModelMapper.
 */
@Service
public class DtoConverter {

    private static final Logger logger = LoggerFactory.getLogger(DtoConverter.class);

    private final ModelMapper modelMapper;

    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Convert an entity to a DTO.
     *
     * @param entity the entity to convert
     * @param dtoClass the DTO class type
     * @param <E> the entity type
     * @param <D> the DTO type
     * @return the DTO
     */
    public <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        try {
            logger.info("Converting entity of type {} to DTO of type {}", entity.getClass().getName(), dtoClass.getName());
            D dto = modelMapper.map(entity, dtoClass);
            logger.debug("Conversion successful: {} -> {}", entity.getClass().getName(), dtoClass.getName());
            return dto;
        } catch (Exception e) {
            logger.error("Error converting entity {} to DTO {}: {}", entity.getClass().getName(), dtoClass.getName(), e.getMessage());
            throw new RuntimeException("Failed to convert entity to DTO", e);
        }
    }

    /**
     * Convert a DTO to an entity.
     *
     * @param dto the DTO to convert
     * @param entityClass the entity class type
     * @param <E> the entity type
     * @param <D> the DTO type
     * @return the entity
     */
    public <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        try {
            logger.info("Converting DTO of type {} to entity of type {}", dto.getClass().getName(), entityClass.getName());
            E entity = modelMapper.map(dto, entityClass);
            logger.debug("Conversion successful: {} -> {}", dto.getClass().getName(), entityClass.getName());
            return entity;
        } catch (Exception e) {
            logger.error("Error converting DTO {} to entity {}: {}", dto.getClass().getName(), entityClass.getName(), e.getMessage());
            throw new RuntimeException("Failed to convert DTO to entity", e);
        }
    }
}