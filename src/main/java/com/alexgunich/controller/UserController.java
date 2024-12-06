package com.alexgunich.controller;

import com.alexgunich.dto.UserDto;
import com.alexgunich.model.User;
import com.alexgunich.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с пользователями.
 * Обрабатывает входящие HTTP-запросы и вызывает методы UserService.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получить всех пользователей
     * @return список DTO пользователей
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    /**
     * Получить пользователя по ID
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    /**
     * Создать нового пользователя.
     *
     * @param user данные нового пользователя.
     * @return созданный пользователь.
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Удалить пользователя по ID.
     *
     * @param id идентификатор пользователя.
     * @return статус 204 (No Content), если пользователь успешно удален.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
