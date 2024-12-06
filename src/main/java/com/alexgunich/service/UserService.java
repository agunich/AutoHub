package com.alexgunich.service;

import com.alexgunich.dto.UserDto;
import com.alexgunich.model.User;
import com.alexgunich.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервисный класс для работы с пользователями.
 * Содержит бизнес-логику и взаимодействует с репозиторием UserRepository.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загрузка пользователя по имени пользователя (для аутентификации).
     *
     * @param username имя пользователя
     * @return UserDetails объект с данными пользователя
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден с email: " + username));
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

    // Дополнительные методы для работы с пользователями
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Получить всех пользователей
     * @return список DTO пользователей
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto) // Преобразуем сущности в DTO
                .collect(Collectors.toList());
    }

    /**
     * Получить пользователя по ID
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDto(user);
    }

    /**
     * Преобразовать сущность в DTO
     * @param user сущность пользователя
     * @return DTO пользователя
     */
    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole().name()
        );
    }

    /**
     * Создать нового пользователя.
     *
     * @param user данные нового пользователя.
     * @return созданный пользователь.
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Удалить пользователя по ID.
     *
     * @param id идентификатор пользователя.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}