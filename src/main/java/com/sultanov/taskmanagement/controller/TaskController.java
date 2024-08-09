package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    public TaskDto createTask(@Valid @RequestBody TaskDto taskDTO) {
        return taskService.createTask(taskDTO);
    }

    @PutMapping("/update/{taskId}")
    public TaskDto updateTask(@PathVariable Long taskId,@Valid  @RequestBody TaskDto taskDTO) {
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