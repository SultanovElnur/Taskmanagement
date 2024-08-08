package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.TaskDto;
import com.sultanov.taskmanagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskDto createTask(@RequestBody TaskDto taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping("/{taskId}")
    public TaskDto updateTask(@PathVariable Long taskId, @RequestBody TaskDto taskDTO) {
        return taskService.updateTask(taskId, taskDTO);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/author/{authorId}")
    public List<TaskDto> getTasksByAuthorId(@PathVariable Long authorId) {
        return taskService.getTasksByAuthorId(authorId);
    }

    @GetMapping("/assignee/{assigneeId}")
    public List<TaskDto> getTasksByAssigneeId(@PathVariable Long assigneeId) {
        return taskService.getTasksByAssigneeId(assigneeId);
    }
}