package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.dto.task.TaskUpdateDto;
import com.sultanov.taskmanagement.mapper.TaskMapper;
import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.repository.TaskRepository;
import com.sultanov.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
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
        Task task = taskMapper.toEntity(taskDTO);
        User author = userRepository.findByEmail(taskDTO.getAuthor().getEmail());
        User assignee = userRepository.findByEmail(taskDTO.getAssignee().getEmail());
        task.setAuthor(author);
        if (taskDTO.getAssignee() != null) {
            task.setAssignee(assignee);
        }
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    public TaskDto updateTask(Long taskId, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        taskMapper.updateFromDTO(taskUpdateDto, task);
        return taskMapper.toDto(taskRepository.save(task));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public TaskDto getTaskById(Long taskId) {
        return taskMapper.toDto(taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found")));
    }

    public List<TaskDto> getTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    public List<TaskDto> getAllTask() {
        return taskMapper.allToDto(taskRepository.findAll());
    }
}