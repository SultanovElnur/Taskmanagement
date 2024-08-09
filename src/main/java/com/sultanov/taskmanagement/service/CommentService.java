package com.sultanov.taskmanagement.service;

import com.sultanov.taskmanagement.dto.comment.CommentDto;
import com.sultanov.taskmanagement.mapper.CommentMapper;
import com.sultanov.taskmanagement.model.entity.Comment;
import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.repository.CommentRepository;
import com.sultanov.taskmanagement.repository.TaskRepository;
import com.sultanov.taskmanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public CommentDto addComment(Long taskId, CommentDto commentDTO) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
        User user = userRepository.findByEmail(commentDTO.getUser().getEmail());
        Comment comment = commentMapper.toEntity(commentDTO);
        comment.setTask(task);
        comment.setUser(user);
        return commentMapper.toDto(commentRepository.save(comment));
    }

    public List<CommentDto> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId).stream().map(commentMapper::toDto).collect(Collectors.toList());
    }
}