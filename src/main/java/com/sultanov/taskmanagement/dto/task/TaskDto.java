package com.sultanov.taskmanagement.dto.task;

import com.sultanov.taskmanagement.dto.comment.CommentDto;
import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.model.enums.Priority;
import com.sultanov.taskmanagement.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UserDto author;
    private UserDto assignee;
    private Set<CommentDto> comments;
}