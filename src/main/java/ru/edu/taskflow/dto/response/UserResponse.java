package ru.edu.taskflow.dto.response;

import ru.edu.taskflow.entity.RoleName;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        RoleName roleName,
        boolean enabled,
        LocalDateTime createdAt
) {}
