package com.sultanov.taskmanagement.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredDto {
    @NotBlank(message = "Please enter your user name")
    private String userName;
    @Email
    @NotBlank(message = "Please enter your email address")
    private String email;
    @Size(min = 10, max = 30, message = "Password must be between 10 and 30 characters")
    @NotBlank(message = "Please enter your password")
    private String password;
}
