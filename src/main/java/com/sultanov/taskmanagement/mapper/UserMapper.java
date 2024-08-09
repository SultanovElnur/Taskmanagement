package com.sultanov.taskmanagement.mapper;

import com.sultanov.taskmanagement.dto.user.UserRegisterDto;
import com.sultanov.taskmanagement.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract User toEntity(UserRegisterDto userRegisterDto);

}
