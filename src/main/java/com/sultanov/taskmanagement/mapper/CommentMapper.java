package com.sultanov.taskmanagement.mapper;

import com.sultanov.taskmanagement.dto.comment.CommentDto;
import com.sultanov.taskmanagement.model.entity.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {

    public abstract CommentDto toDto(Comment comment);

    public abstract Comment toEntity(CommentDto commentDTO);
}