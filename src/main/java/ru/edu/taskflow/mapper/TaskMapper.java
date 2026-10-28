package ru.edu.taskflow.mapper;

import org.mapstruct.*;
import ru.edu.taskflow.dto.request.CreateTaskRequest;
import ru.edu.taskflow.dto.request.UpdateTaskRequest;
import ru.edu.taskflow.dto.response.TaskResponse;
import ru.edu.taskflow.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "creator", ignore = true)
    @Mapping(target = "assignee", ignore = true)
    Task toTaskEntity(CreateTaskRequest createTaskRequest);

    TaskResponse toTaskResponse(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void updateTaskFromDto(UpdateTaskRequest updateTaskRequest, @MappingTarget Task task);
}
