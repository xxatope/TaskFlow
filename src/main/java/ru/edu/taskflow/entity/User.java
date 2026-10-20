package ru.edu.taskflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private boolean enabled;

    private String passwordHash;

    private String fullName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;


    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
