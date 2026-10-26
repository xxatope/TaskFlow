package ru.edu.taskflow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.edu.taskflow.dto.request.RegisterRequest;
import ru.edu.taskflow.dto.response.UserResponse;
import ru.edu.taskflow.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "role.name", target = "roleName")
    UserResponse toResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passwordHash", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User toEntity(RegisterRequest request);
}
