package com.elevate.ElevateBackend.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elevate.ElevateBackend.entity.Task;
import com.elevate.ElevateBackend.entity.User;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser(User user);

    List<Task> findByUserAndCompleted(User user, boolean completed);

    Optional<Task> findByIdAndUser(Long id, User user);
    
    long countByUser(User user);

    long countByUserAndCompleted(User user, boolean completed);

    long countByUserAndDueDate(User user, LocalDate dueDate);

    long countByUserAndDueDateBeforeAndCompleted(User user,
                                                 LocalDate date,
                                                 boolean completed);
    
    Page<Task> findByUser(User user, Pageable pageable);

    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String title);

    List<Task> findByUserAndDueDate(User user, LocalDate dueDate);

    List<Task> findByUserOrderByDueDateAsc(User user);

    List<Task> findByUserOrderByCreatedAtDesc(User user);

}