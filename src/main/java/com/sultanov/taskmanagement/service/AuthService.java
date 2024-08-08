package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.user.UserLoginDto;
import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.dto.user.UserRegisterDto;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserDto register(@RequestBody UserRegisterDto userRegisterDto) {
        User savedUser = userService.save(userRegisterDto);
        return UserDto.builder()
                .id(savedUser.getId())
                .email(userRegisterDto.getEmail())
                .build();
    }

    public String login(@RequestBody UserLoginDto userLoginDto) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword())
            );
        } catch (Exception e) {
            throw new Exception("Invalid credentials", e);
        }
        final UserDetails userDetails = userService.loadUserByUsername(userLoginDto.getEmail());
        return jwtUtil.generateToken(userDetails);
    }
}
