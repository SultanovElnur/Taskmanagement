package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.auth.AuthResponseDto;
import com.sultanov.taskmanagement.dto.error.ErrorResponseDto;
import com.sultanov.taskmanagement.dto.user.UserLoginDto;
import com.sultanov.taskmanagement.dto.user.UserRegisterDto;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.repository.UserRepository;
import com.sultanov.taskmanagement.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public ResponseEntity<?> register(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponseDto("User has been already registered!"));
        }

        User savedUser = userService.save(userRegisterDto);
        UserDetails userDetails = userService.loadUserByUsername(savedUser.getEmail());
        String token = jwtUtil.generateToken(userDetails, savedUser.getRole());

        AuthResponseDto response = new AuthResponseDto(
                savedUser.getUserName(),
                "Account created successfully",
                token
        );
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> login(UserLoginDto userLoginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginDto.getEmail(),
                            userLoginDto.getPassword()
                    )
            );

            User user = userRepository.findByEmail(userLoginDto.getEmail());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponseDto("Incorrect credentials"));
            }

            UserDetails userDetails = userService.loadUserByUsername(userLoginDto.getEmail());
            String token = jwtUtil.generateToken(userDetails, user.getRole());

            AuthResponseDto response = new AuthResponseDto(
                    user.getUserName(),
                    "Logged In",
                    token
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDto("Incorrect credentials"));
        }
    }
}

