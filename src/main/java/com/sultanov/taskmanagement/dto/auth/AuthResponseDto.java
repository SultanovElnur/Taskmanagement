package com.sultanov.taskmanagement.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String username;
    private String message;
    private String token;
}