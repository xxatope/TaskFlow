package ru.edu.taskflow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.edu.taskflow.entity.AuditLog;
import ru.edu.taskflow.event.TaskEvent;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "entityType", constant = "TASK")
    @Mapping(source = "taskId", target = "entityId")
    @Mapping(source = "eventType", target = "action")
    @Mapping(source = "timestamp", target = "occurredAt")
    AuditLog toEntity(TaskEvent event);

}
