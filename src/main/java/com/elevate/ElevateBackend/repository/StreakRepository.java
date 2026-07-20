package com.elevate.ElevateBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elevate.ElevateBackend.entity.Streak;
import com.elevate.ElevateBackend.entity.User;

public interface StreakRepository extends JpaRepository<Streak, Long>{

    Optional<Streak> findByUser(User user);

}