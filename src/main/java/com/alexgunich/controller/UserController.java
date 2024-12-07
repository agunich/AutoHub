package com.alexgunich.controller;

import com.alexgunich.dto.UserDto;
import com.alexgunich.model.User;
import com.alexgunich.service.UserService;
import com.alexgunich.util.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с пользователями.
 * Обрабатывает входящие HTTP-запросы и вызывает методы UserService.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final DtoConverter dtoConverter;

    @Autowired
    public UserController(UserService userService, DtoConverter dtoConverter) {
        this.userService = userService;
        this.dtoConverter = dtoConverter;
    }

    /**
     * Получить всех пользователей
     * @return список DTO пользователей
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers().stream()
                .map(user -> dtoConverter.convertToDto(user, UserDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить пользователя по ID
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return dtoConverter.convertToDto(userService.getUserById(id), UserDto.class);
    }

    /**
     * Создать нового пользователя.
     *
     * @param user данные нового пользователя.
     * @return созданный пользователь.
     */
    @PostMapping
    public User createUser(@RequestBody @Valid UserDto userDto) {
        return userService.createUser(
                dtoConverter.convertToEntity(userDto, User.class)
        );
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
