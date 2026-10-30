package ru.edu.taskflow.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edu.taskflow.dto.request.CreateTaskRequest;
import ru.edu.taskflow.dto.request.UpdateTaskRequest;
import ru.edu.taskflow.dto.response.TaskResponse;
import ru.edu.taskflow.entity.Task;
import ru.edu.taskflow.entity.TaskStatus;
import ru.edu.taskflow.entity.User;
import ru.edu.taskflow.exception.ResourceNotFoundException;
import ru.edu.taskflow.mapper.TaskMapper;
import ru.edu.taskflow.repository.TaskRepository;
import ru.edu.taskflow.repository.UserRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }

    @Transactional
    public TaskResponse createTask(CreateTaskRequest createTaskRequest, String creatorEmail){
        User user = userRepository.findByEmail(creatorEmail)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Creator not found"));
        ru.edu.taskflow.entity.Task task = taskMapper.toTaskEntity(createTaskRequest);
        task.setCreator(user);
        task.setStatus(TaskStatus.DRAFT);

        return taskMapper.toTaskResponse(taskRepository.save(task));
    }

    @Transactional
    public TaskResponse updateTask(Long id,UpdateTaskRequest updateTaskRequest){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException ("Task not found"));
        taskMapper.updateTaskFromDto(updateTaskRequest, task);
        if (updateTaskRequest.assigneeId() != null) {
            User assignee = userRepository.findById(updateTaskRequest.assigneeId())
                    .orElseThrow(()
                            -> new ResourceNotFoundException ("Assignee not found"));
            task.setAssignee(assignee);
        }
        return taskMapper.toTaskResponse(taskRepository.save(task));
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
