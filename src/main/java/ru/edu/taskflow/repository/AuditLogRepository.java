package ru.edu.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskflow.entity.AuditLog;

import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findAllByEntityTypeAndEntityIdOrderByOccurredAtDesc(String entityType,Long entityId);

}
