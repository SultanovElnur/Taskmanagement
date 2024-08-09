package com.sultanov.taskmanagement.dto.task;

import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.model.enums.Priority;
import com.sultanov.taskmanagement.model.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDto {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private UserDto author;
    private UserDto assignee;
}
