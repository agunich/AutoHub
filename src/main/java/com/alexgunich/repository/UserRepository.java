package com.alexgunich.repository;

import com.alexgunich.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for working with users.
 * Uses JPA for database interaction and Redis for caching.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by their email with caching enabled in Redis.
     *
     * @param email the email of the user.
     * @return an Optional containing the user if found, otherwise empty.
     */
    //FIXME  включить кэширование email при росте нагрузки!
    //@Cacheable(value = "userByEmail", key = "#email")
    Optional<User> findByEmail(String email);
}