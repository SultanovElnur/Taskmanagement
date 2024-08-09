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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @CacheEvict(value = "TaskCache", allEntries = true)
    @CachePut(value = "TaskCache", key = "#result.id")
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
    @CacheEvict(value = "TaskCache", allEntries = true)
    @CachePut(value = "TaskCache", key = "#taskId")
    public TaskDto updateTask(Long taskId, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!task.getAssignee().equals(currentUser)) {
            throw new RuntimeException("You are not authorized to update this task.");
        }

        taskMapper.updateFromDTO(taskUpdateDto, task);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @CacheEvict(value = "TaskCache", key = "#taskId")
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Cacheable(value = "TaskCache", key = "#taskId")
    public TaskDto getTaskById(Long taskId) {
        return taskMapper.toDto(taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found")));
    }

    @Cacheable(value = "TaskCache", key = "#authorId")
    public List<TaskDto> getTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Cacheable(value = "TaskCache", key = "#assigneeId")
    public List<TaskDto> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream().map(taskMapper::toDto).collect(Collectors.toList());
    }

    @Cacheable(value = "TaskCache")
    public List<TaskDto> getAllTask() {
        return taskMapper.allToDto(taskRepository.findAll());
    }
}