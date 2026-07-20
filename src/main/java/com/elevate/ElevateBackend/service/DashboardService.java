package com.elevate.ElevateBackend.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.DashboardResponse;
import com.elevate.ElevateBackend.dto.StreakResponse;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.TaskRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final StreakService streakService;

    public DashboardService(
            UserRepository userRepository,
            TaskRepository taskRepository,
            StreakService streakService) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.streakService = streakService;
    }

    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        long totalTasks =
                taskRepository.countByUser(user);

        long completedTasks =
                taskRepository.countByUserAndCompleted(
                        user,
                        true);

        long pendingTasks =
                taskRepository.countByUserAndCompleted(
                        user,
                        false);

        long dueToday =
                taskRepository.countByUserAndDueDate(
                        user,
                        LocalDate.now());

        long overdueTasks =
                taskRepository.countByUserAndDueDateBeforeAndCompleted(
                        user,
                        LocalDate.now(),
                        false);

        double productivity = 0;

        if (totalTasks > 0) {

            productivity =
                    (completedTasks * 100.0) / totalTasks;

        }

        StreakResponse streak =
                streakService.getStreak(email);

        return new DashboardResponse(

                (int) totalTasks,

                (int) completedTasks,

                (int) pendingTasks,

                productivity,

                (int) dueToday,

                (int) overdueTasks,

                streak.getCurrentStreak()

        );

    }

}