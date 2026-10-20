package ru.edu.taskflow.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
