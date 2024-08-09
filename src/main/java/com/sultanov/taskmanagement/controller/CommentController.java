package com.sultanov.taskmanagement.controller;

import com.sultanov.taskmanagement.dto.comment.CommentDto;
import com.sultanov.taskmanagement.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks/{taskId}/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentDto addComment(@PathVariable Long taskId, @Valid @RequestBody CommentDto commentDTO) {
        return commentService.addComment(taskId, commentDTO);
    }

    @GetMapping
    public List<CommentDto> getCommentsByTaskId(@PathVariable Long taskId) {
        return commentService.getCommentsByTaskId(taskId);
    }
}