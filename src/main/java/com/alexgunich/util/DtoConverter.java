package com.alexgunich.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DtoConverter {

    private final ModelMapper modelMapper;

    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Преобразовать сущность в DTO
     * @param entity сущность
     * @param dtoClass класс DTO
     * @param <E> тип сущности
     * @param <D> тип DTO
     * @return DTO
     */
    public <E, D> D convertToDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Преобразовать DTO в сущность
     * @param dto DTO
     * @param entityClass класс сущности
     * @param <E> тип сущности
     * @param <D> тип DTO
     * @return сущность
     */
    public <E, D> E convertToEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }
}
