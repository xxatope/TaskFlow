package ru.edu.taskflow.dto.response;

import ru.edu.taskflow.entity.ApprovalStatus;

import java.time.LocalDateTime;

public record ApprovalResponse(
        Long id,
        Long taskId,
        String taskTitle,
        Long approverId,
        String approverName,
        ApprovalStatus status,
        String comment,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt
) {}
