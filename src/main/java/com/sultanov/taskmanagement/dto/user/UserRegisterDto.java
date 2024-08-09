package com.sultanov.taskmanagement.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @NotBlank(message = "Please enter your user name")
    private String userName;
    @Email(message = "wrong email format")
    @NotBlank(message = "Please enter your email address")
    private String email;
    @Length(min = 10, max = 30, message = "Password must be between 10 and 30 characters")
    @NotBlank(message = "Please enter your password")
    private String password;
}
