package com.sultanov.taskmanagement.service;


import com.sultanov.taskmanagement.dto.user.UserCredDto;
import com.sultanov.taskmanagement.exception.EmailAlreadyExistsException;
import com.sultanov.taskmanagement.mapper.UserMapper;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.model.enums.Role;
import com.sultanov.taskmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public User save(UserCredDto userCredDto) {
        if (userRepository.existsByEmail(userCredDto.getEmail())) {
            throw new EmailAlreadyExistsException("Email already in use");
        }

        userCredDto.setPassword(passwordEncoder.encode(userCredDto.getPassword()));
        User user = userMapper.mapToEntity(userCredDto);
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("USER")
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}