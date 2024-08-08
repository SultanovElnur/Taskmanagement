package com.sultanov.taskmanagement.dto;

import com.sultanov.taskmanagement.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private UserDto author;
    private UserDto assignee;
    private Set<CommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}