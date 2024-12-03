package com.example.validation.domain.service;

import com.example.validation.domain.dto.UserDto;
import com.example.validation.domain.exceptions.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private TempService tempService;

    public UserDto addUser(UserDto userDto) {
        Map<String, List<String>> errors = new HashMap<>();

        String password = userDto.getPassword();
        // TODO: если выполнять проверку в сервисах, то имеем проблему с фидбеком к пользователю - он сразу не узнает, где допустил ошибку
        if (password == null) {
            throw new NullPointerException("Password is null");
        }

        if (password.isBlank()) {
            errors.computeIfAbsent("password", k -> new ArrayList<>()).add("Пароль не может быть пустым");
        }

        if (password.length() < 6) {
            errors.computeIfAbsent("password", k -> new ArrayList<>()).add("Пароль не может быть менее 6 символов");
        }

        if (password.length() > 20) {
            errors.computeIfAbsent("password", k -> new ArrayList<>()).add("Пароль не может быть более 20 символов");
        }

//        if (!tempService.exists(false)) {  // Перенёс эту логику в TempServiceValidator
//            errors.computeIfAbsent("temp", k -> new ArrayList<>()).add("Сторонний сервис вернул false");
//        }

        if (!errors.isEmpty()) {
            throw new BadRequestException(errors);
        }

        return userDto;
    }

}
