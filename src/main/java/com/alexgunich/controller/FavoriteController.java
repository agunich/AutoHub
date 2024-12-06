package com.alexgunich.controller;

import com.alexgunich.dto.FavoriteDto;
import com.alexgunich.dto.UserDto;
import com.alexgunich.model.Favorite;
import com.alexgunich.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с избранным.
 */
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Autowired
    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    /**
     * Получить список всех избранных записей.
     *
     * @return список избранных записей.
     */
    @GetMapping
    public List<FavoriteDto> getAllFavorites() {
        return favoriteService.getAllFavorites();
    }

    /**
     * Получить запись в избранном по ID.
     *
     * @param id идентификатор записи.
     * @return запись или статус 404, если запись не найдена.
     */
    @GetMapping("/{id}")
    public FavoriteDto getFavoriteById(@PathVariable Long id) {
        return favoriteService.getFavoriteById(id);
    }

    /**
     * Добавить новую запись в избранное.
     *
     * @param favorite данные записи.
     * @return созданная запись.
     */
    @PostMapping
    public Favorite createFavorite(@RequestBody Favorite favorite) {
        return favoriteService.createFavorite(favorite);
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
