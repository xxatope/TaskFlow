package ru.edu.taskflow.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ApprovalDecisionRequest(
        @NotBlank
        String comment
) {}
