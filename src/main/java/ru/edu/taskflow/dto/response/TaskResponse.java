package ru.edu.taskflow.dto.response;

import ru.edu.taskflow.entity.TaskPriority;
import ru.edu.taskflow.entity.TaskStatus;

import java.time.LocalDateTime;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        TaskPriority priority,
        LocalDateTime dueDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long creatorId,
        String creatorName,
        Long assigneeId,
        String assigneeName
) {}
