package com.sultanov.taskmanagement.repository;


import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    boolean existsByEmail(String email);

    Page<Task> findByEmail(String email, Pageable pageable);
}