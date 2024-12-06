package com.alexgunich.service;

import com.alexgunich.dto.FavoriteDto;
import com.alexgunich.model.Favorite;
import com.alexgunich.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный класс для работы с избранными автомобилями.
 */
@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * Получить все записи в избранном с кешированием.
     * @return список DTO избранных записей.
     */
    @Cacheable(value = "favorites", key = "'allFavorites'")
    public List<FavoriteDto> getAllFavorites() {
        List<Favorite> favorites = favoriteRepository.findAll();
        return favorites.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Получить запись избранного по ID с кешированием.
     * @param id идентификатор записи.
     * @return DTO избранной записи.
     */
    @Cacheable(value = "favorites", key = "#id")
    public FavoriteDto getFavoriteById(Long id) {
        Favorite favorite = favoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
        return convertToDto(favorite);
    }

    /**
     * Создать новую запись в избранном.
     * @param favorite данные новой записи.
     * @return созданная запись.
     */
    @CachePut(value = "favorites", key = "#result.id")
    @CacheEvict(value = "favorites", key = "'allFavorites'")
    public Favorite createFavorite(Favorite favorite) {
        return favoriteRepository.save(favorite);
    }

    /**
     * Удалить запись из избранного по ID и обновить кеш.
     * @param id идентификатор записи.
     */
    @CacheEvict(value = "favorites", key = "#id")  // Очистка кеша для конкретной записи
    public void deleteFavorite(Long id) {
        favoriteRepository.deleteById(id);
    }

    /**
     * Преобразовать сущность в DTO.
     * @param favorite сущность избранного.
     * @return DTO избранной записи.
     */
    private FavoriteDto convertToDto(Favorite favorite) {
        return new FavoriteDto(
                favorite.getId(),
                favorite.getUser().getId(),
                favorite.getCar().getId()
        );
    }
}
