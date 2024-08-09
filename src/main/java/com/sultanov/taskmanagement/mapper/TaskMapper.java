package com.sultanov.taskmanagement.mapper;

import com.sultanov.taskmanagement.dto.task.TaskDto;
import com.sultanov.taskmanagement.dto.task.TaskUpdateDto;
import com.sultanov.taskmanagement.model.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TaskMapper {

    public abstract TaskDto toDto(Task task);

    public abstract Task toEntity(TaskDto taskDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateFromDTO(TaskUpdateDto taskUpdateDto, @MappingTarget Task task);

    public abstract List<TaskDto> allToDto(List<Task> all);
}