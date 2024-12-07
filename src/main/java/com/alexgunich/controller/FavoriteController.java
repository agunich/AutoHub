package com.alexgunich.controller;

import com.alexgunich.dto.FavoriteDto;
import com.alexgunich.model.Favorite;
import com.alexgunich.service.FavoriteService;
import com.alexgunich.util.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с избранным.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final DtoConverter dtoConverter;

    @Autowired
    public FavoriteController(FavoriteService favoriteService, DtoConverter dtoConverter) {
        this.favoriteService = favoriteService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Получить список всех избранных записей.
     *
     * @return DTO list избранных записей.
     */
    @GetMapping
    public List<FavoriteDto> getAllFavorites() {
        List<Favorite> favorites = favoriteService.getAllFavorites();
        return favorites.stream()
                .map(favorite -> (dtoConverter.convertToDto(favorite, FavoriteDto.class)))
                .collect(Collectors.toList());
    }

    /**
     * Получить запись в избранном по ID.
     *
     * @param id идентификатор записи.
     * @return DTO запись или статус 404, если запись не найдена.
     */
    @GetMapping("/{id}")
    public FavoriteDto getFavoriteById(@PathVariable Long id) {
        return dtoConverter.convertToDto(favoriteService.getFavoriteById(id), FavoriteDto.class);
    }

    /**
     * Добавить новую запись в избранное.
     *
     * @param favoriteDto данные записи.
     * @return созданная запись.
     */
    @PostMapping
    public ResponseEntity<Void> createFavorite(@RequestBody FavoriteDto favoriteDto) {
        favoriteService.createFavorite(dtoConverter.convertToEntity(favoriteDto, Favorite.class));
        return ResponseEntity.status(201).build();
    }

    /**
     * Удалить запись из избранного.
     *
     * @param id идентификатор записи.
     * @return статус 204 (No Content).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
