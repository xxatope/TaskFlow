package ru.edu.taskflow.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.edu.taskflow.entity.AuditLog;
import ru.edu.taskflow.event.TaskEvent;
import ru.edu.taskflow.repository.AuditLogRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskEventConsumer {

    private final AuditLogRepository auditLogRepository;

    @KafkaListener(
            topics = "${app.kafka.topic.task-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleTaskevent(TaskEvent event){

        log.info("Received event: {}", event);

        AuditLog auditLog = new AuditLog();
        auditLog.setEntityType("TASK");
        auditLog.setEntityId(event.getTaskId());
        auditLog.setAction(event.getEventType().name());
        auditLog.setActorId(event.getActorId());
        auditLog.setDetails(event.getDetails());
        auditLog.setOccurredAt(event.getTimestamp());

        auditLogRepository.save(auditLog);

    }

}
