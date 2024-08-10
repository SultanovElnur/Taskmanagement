package com.sultanov.taskmanagement.repository;

import com.sultanov.taskmanagement.dto.user.UserDto;
import com.sultanov.taskmanagement.model.entity.Task;
import com.sultanov.taskmanagement.model.entity.User;
import com.sultanov.taskmanagement.model.enums.Priority;
import com.sultanov.taskmanagement.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAuthorId(Long authorId);
    List<Task> findByAssigneeId(Long assigneeId);

    Page<Task> findByPriority(Priority priority, Pageable pageable);

    Page<Task> findByStatus(Status status, Pageable pageable);

    Page<Task> findByAuthor(User author, Pageable pageable);

    Page<Task> findByAssignee(User assignee, Pageable pageable);
}