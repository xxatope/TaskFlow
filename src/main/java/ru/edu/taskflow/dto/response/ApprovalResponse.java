package ru.edu.taskflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.edu.taskflow.entity.ApprovalStatus;
import ru.edu.taskflow.entity.TaskPriority;
import ru.edu.taskflow.entity.TaskStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalResponse {

    private Long id;
    private Long taskId;
    private String taskTitle;
    private Long approverId;
    private String approverName;
    private ApprovalStatus status;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}
