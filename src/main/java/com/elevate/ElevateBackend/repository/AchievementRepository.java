package com.elevate.ElevateBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.elevate.ElevateBackend.entity.Achievement;
import com.elevate.ElevateBackend.entity.User;

public interface AchievementRepository
        extends JpaRepository<Achievement,Long>{

    List<Achievement> findByUser(User user);

}