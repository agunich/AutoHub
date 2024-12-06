package com.alexgunich.repository;

import com.alexgunich.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями.
 * Использует JPA для работы с базой данных и Redis для кеширования.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его электронной почте с кешированием в Redis.
     *
     * @param email Электронная почта пользователя.
     * @return Optional<User> С Optional, так как пользователь может не существовать.
     */
    //FIXME  включить кэширование email при росте нагрузки!
    //@Cacheable(value = "userByEmail", key = "#email")
    Optional<User> findByEmail(String email);
}