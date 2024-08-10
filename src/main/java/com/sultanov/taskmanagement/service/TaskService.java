package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.dto.task.TaskUpdateDto;
import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.mapper.TaskMapper;
import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.model.enums.Priority;
import com.sultanov.taskmanagement.model.enums.Status;
import com.sultanov.taskmanagement.repository.TaskRepository;
import com.sultanov.taskmanagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {

    //TODO: add filtering and pagination, also basic testing

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @CacheEvict(value = "TaskCache", allEntries = true)
    @CachePut(value = "TaskCache")
    public TaskDto createTask(TaskDto taskDTO) {
        Task task = taskMapper.mapToEntity(taskDTO);
        User author = userRepository.findByEmail(taskDTO.getAuthor().getEmail());
        task.setAuthor(author);
        if (taskDTO.getAssignee() != null) {
            User assignee = userRepository.findByEmail(taskDTO.getAssignee().getEmail());
            task.setAssignee(assignee);
        }
        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @Transactional
    @CacheEvict(value = "TaskCache", allEntries = true)
    @CachePut(value = "TaskCache")
    public TaskDto updateTask(Long taskId, TaskUpdateDto taskUpdateDto) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!task.getAssignee().equals(currentUser)) {
            throw new RuntimeException("You are not authorized to update this task.");
        }

        taskMapper.updateEntityFromDTO(taskUpdateDto, task);
        return taskMapper.mapToDto(taskRepository.save(task));
    }

    @CacheEvict(value = "TaskCache")
    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Cacheable(value = "TaskCache")
    public TaskDto getTaskById(Long taskId) {
        return taskMapper.mapToDto(taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found")));
    }

    @Cacheable(value = "TaskCache")
    public List<TaskDto> getTasksByAuthorId(Long authorId) {
        return taskRepository.findByAuthorId(authorId).stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    @Cacheable(value = "TaskCache")
    public List<TaskDto> getTasksByAssigneeId(Long assigneeId) {
        return taskRepository.findByAssigneeId(assigneeId).stream().map(taskMapper::mapToDto).collect(Collectors.toList());
    }

    @Cacheable(value = "TaskCache")
    public Map<String, Object> getAllTask(UserDto author, UserDto assignee, Priority priority, Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks;
        User user;

        if (author != null) {
            user = userRepository.findByEmail(author.getEmail());
            tasks = taskRepository.findByAuthor(user, pageable);
        } else if (assignee != null) {
            user = userRepository.findByEmail(assignee.getEmail());
            tasks = taskRepository.findByAssignee(user, pageable);
        } else if (priority != null) {
            tasks = taskRepository.findByPriority(priority, pageable);
        }else if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        return Map.of("allSalesHistory", tasks.getContent(),
                "currentPage", tasks.getNumber(),
                "totalElements", tasks.getTotalElements(),
                "totalPages", tasks.getTotalPages());
    }
}