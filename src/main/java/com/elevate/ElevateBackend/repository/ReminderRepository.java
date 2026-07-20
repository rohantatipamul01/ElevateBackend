package com.elevate.ElevateBackend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elevate.ElevateBackend.entity.Reminder;
import com.elevate.ElevateBackend.entity.User;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByUser(User user);

    Optional<Reminder> findByIdAndUser(Long id, User user);

    List<Reminder> findByUserAndCompleted(User user, boolean completed);

    List<Reminder> findByUserAndReminderTimeBetween(
            User user,
            LocalDateTime start,
            LocalDateTime end);

    long countByUser(User user);

    long countByUserAndCompleted(User user, boolean completed);

}