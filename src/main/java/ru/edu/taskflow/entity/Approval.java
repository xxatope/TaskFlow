package ru.edu.taskflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "approvals")
public class Approval {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;


    private LocalDateTime resolvedAt;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "approver_id")
    private User approver;
}
