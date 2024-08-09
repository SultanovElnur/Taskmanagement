package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.user.UserCredDto;
import com.sultanov.taskmanagement.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserCredDto userCredDto) {
        return authService.register(userCredDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserCredDto userCredDto){
        return authService.login(userCredDto);
    }
}