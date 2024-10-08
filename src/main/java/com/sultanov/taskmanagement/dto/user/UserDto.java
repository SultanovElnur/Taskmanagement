package com.sultanov.taskmanagement.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Email(message = "wrong email format")
    @NotBlank(message = "Please enter your email address")
    private String email;
}