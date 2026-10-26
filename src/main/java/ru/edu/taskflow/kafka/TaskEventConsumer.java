package ru.edu.taskflow.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.edu.taskflow.entity.AuditLog;
import ru.edu.taskflow.event.TaskEvent;
import ru.edu.taskflow.mapper.AuditLogMapper;
import ru.edu.taskflow.repository.AuditLogRepository;

@Component
public class TaskEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(TaskEventConsumer.class);

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    public TaskEventConsumer(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.task-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleTaskevent(TaskEvent event) {
        log.info("Received event: {}", event);
        AuditLog auditLog = auditLogMapper.toEntity(event);
        auditLogRepository.save(auditLog);
    }
}
