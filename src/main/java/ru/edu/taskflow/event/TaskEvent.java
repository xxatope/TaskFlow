package ru.edu.taskflow.event;

import java.time.LocalDateTime;

public record TaskEvent(
        TaskEventType eventType,
        Long taskId,
        Long actorId,
        String oldStatus,
        String newStatus,
        String details,
        LocalDateTime timestamp
) {
    public TaskEvent(TaskEventType eventType, Long taskId, Long actorId, String details, LocalDateTime timestamp) {
        this(eventType, taskId, actorId, null, null, details, timestamp);
    }
}
