package com.alexgunich.service;

import com.alexgunich.model.User;
import com.alexgunich.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


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

    /**
     * Получить всех пользователей
     * @return список DTO пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получить пользователя по ID
     * @param id идентификатор пользователя
     * @return DTO пользователя
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
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