package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.dto.task.TaskUpdateDto;
import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.model.enums.Priority;
import com.sultanov.taskmanagement.model.enums.Status;
import com.sultanov.taskmanagement.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
    public TaskDto updateTask(@PathVariable Long taskId, @Valid @RequestBody TaskUpdateDto taskUpdateDto) {
        return taskService.updateTask(taskId, taskUpdateDto);
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
    }

    @GetMapping("/{taskId}")
    public TaskDto getTaskById(@PathVariable Long taskId) {
        return taskService.getTaskById(taskId);
    }

    @GetMapping("/all")
    public Map<String, Object> getAllTask(@RequestParam(required = false) UserDto author,
                                          @RequestParam(required = false) UserDto assignee,
                                          @RequestParam(required = false) Priority priority,
                                          @RequestParam(required = false) Status status,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        return taskService.getAllTask(author, assignee, priority, status, page, size);
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