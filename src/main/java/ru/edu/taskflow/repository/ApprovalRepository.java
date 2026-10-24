package ru.edu.taskflow.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.taskflow.entity.Approval;
import ru.edu.taskflow.entity.ApprovalStatus;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    @EntityGraph(attributePaths = {"task", "approver"})
    List<Approval> findAllByTaskId(Long taskId);

    @EntityGraph(attributePaths = {"task", "approver"})
    List<Approval> findAllByApproverIdAndStatus(Long approverId, ApprovalStatus status);
}
