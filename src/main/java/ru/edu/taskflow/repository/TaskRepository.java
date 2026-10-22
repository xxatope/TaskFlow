package ru.edu.taskflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskflow.entity.Task;
import ru.edu.taskflow.entity.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

     List<Task> findAllByAssigneeId(Long assigneeId);

     List<Task> findAllByStatus(TaskStatus status);

     List<Task> findAllByCreatorId(Long creatorId);

}
