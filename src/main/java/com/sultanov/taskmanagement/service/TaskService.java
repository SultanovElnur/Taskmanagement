package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.mapper.TaskMapper;
import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.repository.TaskRepository;
import com.sultanov.taskmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskDto createTask(TaskDto taskDTO) {
        Task task = taskMapper.mapToEntity(taskDTO);
        task.setAuthor(userRepository.findByEmail(taskDTO.getAuthor().getEmail()));
        if (taskDTO.getAssignee() != null) {
            task.setAssignee(userRepository.findByEmail(taskDTO.getAssignee().getEmail()));
        }
        return taskMapper.mapToDto(taskRepository.save(task));
    }

    public TaskDto updateTask(Long taskId, TaskDto taskDTO) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskMapper.updateEntityFromDTO(taskDTO, task);
        return taskMapper.mapToDto(taskRepository.save(task));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TaskDto getTaskById(Long taskId) {
        return taskMapper.mapToDto(taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found")));
    }

    public List<TaskDto> getTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId).stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }
}