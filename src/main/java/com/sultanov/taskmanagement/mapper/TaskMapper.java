package com.sultanov.taskmanagement.mapper;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.model.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    public abstract TaskDto mapToDto(Task task);

    public abstract Task mapToEntity(TaskDto taskDTO);

    public abstract void updateEntityFromDTO(TaskDto taskDTO, @MappingTarget Task task);
}