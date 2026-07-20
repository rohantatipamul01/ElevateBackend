package com.elevate.ElevateBackend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elevate.ElevateBackend.entity.Habit;
import com.elevate.ElevateBackend.entity.User;

public interface HabitRepository extends JpaRepository<Habit, Long> {

    List<Habit> findByUser(User user);

    List<Habit> findByUserAndActive(User user, boolean active);

    Optional<Habit> findByIdAndUser(Long id, User user);

    long countByUser(User user);

    long countByUserAndActive(User user, boolean active);

}