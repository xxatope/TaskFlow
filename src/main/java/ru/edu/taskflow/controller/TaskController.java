package ru.edu.taskflow.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import ru.edu.taskflow.dto.request.CreateTaskRequest;
import ru.edu.taskflow.dto.request.UpdateTaskRequest;
import ru.edu.taskflow.dto.response.TaskResponse;
import ru.edu.taskflow.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody CreateTaskRequest createTaskRequest, @AuthenticationPrincipal UserDetails userDetails){

        return taskService.createTask(createTaskRequest,  userDetails.getUsername());
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id){
        return taskService.getById(id);
    }

    @GetMapping
    public List<TaskResponse> getAll(){
        return taskService.getAll();
    }

    @PatchMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,@Valid @RequestBody UpdateTaskRequest updateTaskRequest){
        return taskService.updateTask(id, updateTaskRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id){
        taskService.deleteTask(id);
    }

}
