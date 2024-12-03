package com.example.validation.domain.controller;

import com.example.validation.domain.dto.UserDto;
import com.example.validation.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RestUserController {
    private final UserService service;

    @PostMapping
    public UserDto addUser(@RequestBody @Valid UserDto userDto) {
        return service.addUser(userDto);
    }

}
