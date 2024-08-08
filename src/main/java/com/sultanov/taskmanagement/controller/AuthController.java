package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.user.UserLoginDto;
import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.dto.user.UserRegisterDto;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
        return authService.register(userRegisterDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDto userLoginDTO) throws Exception {
        return authService.login(userLoginDTO);
    }
}