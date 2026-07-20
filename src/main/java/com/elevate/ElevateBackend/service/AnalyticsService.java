package com.elevate.ElevateBackend.service;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.AnalyticsResponse;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.HabitRepository;
import com.elevate.ElevateBackend.repository.ReminderRepository;
import com.elevate.ElevateBackend.repository.TaskRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class AnalyticsService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final HabitRepository habitRepository;
    private final ReminderRepository reminderRepository;

    public AnalyticsService(
            UserRepository userRepository,
            TaskRepository taskRepository,
            HabitRepository habitRepository,
            ReminderRepository reminderRepository) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.habitRepository = habitRepository;
        this.reminderRepository = reminderRepository;
    }

    public AnalyticsResponse getAnalytics(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        long totalTasks =
                taskRepository.countByUser(user);

        long completedTasks =
                taskRepository.countByUserAndCompleted(user, true);

        long totalHabits =
                habitRepository.countByUser(user);

        long activeHabits =
                habitRepository.countByUserAndActive(user, true);

        long totalReminders =
                reminderRepository.countByUser(user);

        long completedReminders =
                reminderRepository.countByUserAndCompleted(user, true);

        double taskRate = totalTasks == 0
                ? 0
                : completedTasks * 100.0 / totalTasks;

        double reminderRate = totalReminders == 0
                ? 0
                : completedReminders * 100.0 / totalReminders;

        double productivity =
                (taskRate + reminderRate) / 2.0;

        return new AnalyticsResponse(
                totalTasks,
                completedTasks,
                totalHabits,
                activeHabits,
                totalReminders,
                completedReminders,
                taskRate,
                reminderRate,
                productivity
        );
    }
}