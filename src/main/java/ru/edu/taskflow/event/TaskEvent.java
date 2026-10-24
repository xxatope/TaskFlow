package ru.edu.taskflow.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class TaskEvent {

    private TaskEventType eventType;

    private Long taskId;

    private Long actorId;

    private String oldStatus;

    private String newStatus;

    private String details;

    private LocalDateTime timestamp;

}
