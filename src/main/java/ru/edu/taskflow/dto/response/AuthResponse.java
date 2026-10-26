package ru.edu.taskflow.dto.response;

import ru.edu.taskflow.entity.RoleName;

public record AuthResponse(
        String accessToken,
        String tokenType,
        String email,
        RoleName roleName
) {}
