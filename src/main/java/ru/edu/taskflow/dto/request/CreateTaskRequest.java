package ru.edu.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.edu.taskflow.entity.TaskPriority;

import java.time.LocalDateTime;

public record CreateTaskRequest(
        @NotBlank
        @Size(max = 255)
        String title,

        String description,

        @NotNull
        TaskPriority priority,

        LocalDateTime dueDate,

        Long assigneeId
) {}
