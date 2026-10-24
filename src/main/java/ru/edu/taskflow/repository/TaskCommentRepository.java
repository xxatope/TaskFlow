package ru.edu.taskflow.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskflow.entity.TaskComment;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {

    @EntityGraph(attributePaths = {"author"})
    List<TaskComment> findAllByTaskIdOrderByCreatedAtAsc(Long taskId);
}
