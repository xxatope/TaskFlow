package ru.edu.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.edu.taskflow.entity.TaskPriority;

import java.time.LocalDateTime;

public record UpdateTaskRequest(
        @NotBlank
        String title,

        String description,

        @NotNull
        TaskPriority priority,

        LocalDateTime dueDate,

        Long assigneeId
) {}
