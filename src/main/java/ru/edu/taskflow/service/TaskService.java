package ru.edu.taskflow.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.taskflow.dto.request.CreateTaskRequest;
import ru.edu.taskflow.dto.request.UpdateTaskRequest;
import ru.edu.taskflow.dto.response.TaskResponse;
import ru.edu.taskflow.entity.Task;
import ru.edu.taskflow.entity.TaskStatus;
import ru.edu.taskflow.entity.User;
import ru.edu.taskflow.event.TaskEvent;
import ru.edu.taskflow.event.TaskEventType;
import ru.edu.taskflow.exception.ResourceNotFoundException;
import ru.edu.taskflow.kafka.TaskEventProducer;
import ru.edu.taskflow.mapper.TaskMapper;
import ru.edu.taskflow.repository.TaskRepository;
import ru.edu.taskflow.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    private final TaskEventProducer taskEventProducer;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper, TaskEventProducer taskEventProducer) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
        this.taskEventProducer = taskEventProducer;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest createTaskRequest, String creatorEmail){
        User user = userRepository.findByEmail(creatorEmail)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Creator not found"));
        ru.edu.taskflow.entity.Task task = taskMapper.toTaskEntity(createTaskRequest);
        task.setCreator(user);
        task.setStatus(TaskStatus.DRAFT);
        Task response = taskRepository.save(task);
        taskEventProducer.sendEvent(new TaskEvent(TaskEventType.TASK_CREATED, response.getId(),user.getId(),"Task created with title: " + response.getTitle() ,LocalDateTime.now()));
        return taskMapper.toTaskResponse(response);
    }

    @Transactional
    public TaskResponse updateTask(Long id,UpdateTaskRequest updateTaskRequest, String userEmail){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Task not found"));
        User actor = userRepository.findByEmail(userEmail).orElseThrow(()-> new RuntimeException("Actor not found"));
        taskMapper.updateTaskFromDto(updateTaskRequest, task);
        User assignee = null;
        if (updateTaskRequest.assigneeId() != null) {
            assignee = userRepository.findById(updateTaskRequest.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException ("Assignee not found"));
            task.setAssignee(assignee);
        }
        Task savedTask = taskRepository.save(task);
        if(assignee != null){
            taskEventProducer.sendEvent(new TaskEvent(
                    TaskEventType.TASK_ASSIGNED,
                    savedTask.getId(),
                    actor.getId(),
                    "Task assigned to user: " + assignee.getEmail(),
                    LocalDateTime.now()
            ));
        }
        return taskMapper.toTaskResponse(savedTask);
    }

    @Transactional
    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(Long id){
        Task task = taskRepository. findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Not found"));
        return taskMapper.toTaskResponse(task);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAll(){
        return taskRepository.findAll().stream()
                .map(taskMapper :: toTaskResponse)
                .toList();

    }

}
