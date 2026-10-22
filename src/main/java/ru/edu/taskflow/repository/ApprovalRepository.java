package ru.edu.taskflow.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskflow.entity.Approval;
import ru.edu.taskflow.entity.ApprovalStatus;
import ru.edu.taskflow.entity.Task;
import ru.edu.taskflow.entity.User;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    List<Approval> findAllByTaskId(Long taskId);
    List<Approval> findAllByApproverIdAndStatus(Long approverId, ApprovalStatus status);

}
