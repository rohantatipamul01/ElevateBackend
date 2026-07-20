package com.elevate.ElevateBackend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.NotificationResponse;
import com.elevate.ElevateBackend.entity.Task;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.TaskRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class NotificationService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public NotificationService(TaskRepository taskRepository,
                               UserRepository userRepository) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public List<NotificationResponse> getNotifications(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        List<Task> tasks = taskRepository.findByUser(user);

        List<NotificationResponse> notifications = new ArrayList<>();

        for (Task task : tasks) {

            if (!task.isCompleted()) {

                if (task.getDueDate().equals(LocalDate.now())) {

                    notifications.add(

                            new NotificationResponse(

                                    "Due Today",

                                    task.getTitle() + " is due today",

                                    "WARNING"

                            )

                    );

                }

                else if (task.getDueDate().isBefore(LocalDate.now())) {

                    notifications.add(

                            new NotificationResponse(

                                    "Overdue",

                                    task.getTitle() + " is overdue",

                                    "DANGER"

                            )

                    );

                }

            }

        }

        return notifications;

    }
    @Scheduled(cron = "0 0 9 * * ?")
    public void dailyReminder() {

        System.out.println("===============================");
        System.out.println("Checking today's reminders...");
        System.out.println("===============================");

    }

}