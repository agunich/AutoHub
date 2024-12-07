package com.alexgunich.service;

import com.alexgunich.model.Favorite;
import com.alexgunich.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * @return список избранных записей.
     */
    @Cacheable(value = "favorites", key = "'allFavorites'")
    public List<Favorite> getAllFavorites() {
        return favoriteRepository.findAll();
    }

    /**
     * Получить запись избранного по ID с кешированием.
     * @param id идентификатор записи.
     * @return сущность избранной записи.
     */
    @Cacheable(value = "favorites", key = "#id")
    public Favorite getFavoriteById(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favorite not found"));
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
}
