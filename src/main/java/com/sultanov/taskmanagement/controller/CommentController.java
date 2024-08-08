package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.CommentDto;
import com.sultanov.taskmanagement.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto addComment(@PathVariable Long taskId, @RequestBody CommentDto commentDTO) {
        return commentService.addComment(taskId, commentDTO);
    }

    @GetMapping
    public List<CommentDto> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }
}