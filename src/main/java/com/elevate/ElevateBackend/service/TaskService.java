package com.elevate.ElevateBackend.service;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.TaskRequest;
import com.elevate.ElevateBackend.entity.Task;
import com.elevate.ElevateBackend.entity.TaskStatus;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.TaskRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final StreakService streakService;
    private final AchievementService achievementService;

    public TaskService(
            TaskRepository taskRepository,
            UserRepository userRepository,
            StreakService streakService,
            AchievementService achievementService) {

        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.streakService = streakService;
        this.achievementService = achievementService;
    }

    // ================= CREATE TASK =================

    public Task createTask(TaskRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Task task = new Task();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        task.setStatus(request.getStatus());

        task.setProgress(
                request.getProgress() != null
                        ? request.getProgress()
                        : 0
        );

        task.setCompleted(
                request.getStatus() == TaskStatus.DONE
        );

        task.setUser(user);

        return taskRepository.save(task);
    }

    // ================= GET ALL TASKS =================

    public List<Task> getAllTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUser(user);
    }

    // ================= GET SINGLE TASK =================

    public Task getTask(Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));
    }

    // ================= DELETE TASK =================

    public void deleteTask(Long id, String email) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));

        if (!task.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        taskRepository.delete(task);
    }

    // ================= UPDATE TASK =================

    public Task updateTask(
            Long id,
            TaskRequest request,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());

        task.setPriority(request.getPriority());
        task.setCategory(request.getCategory());
        task.setStatus(request.getStatus());

        task.setProgress(
                request.getProgress() != null
                        ? request.getProgress()
                        : task.getProgress()
        );

        task.setCompleted(
                request.getStatus() == TaskStatus.DONE
        );

        return taskRepository.save(task);
    }

    // ================= COMPLETE TASK =================

    public Task markCompleted(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));

        task.setCompleted(true);
        task.setProgress(100);
        task.setStatus(TaskStatus.DONE);

        achievementService.checkAchievements(user);
        streakService.updateStreak(user);

        return taskRepository.save(task);
    }

    // ================= UPDATE PROGRESS =================

    public Task updateProgress(
            Long id,
            Integer progress,
            String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Task Not Found"));

        task.setProgress(progress);

        if (progress >= 100) {

            task.setCompleted(true);
            task.setStatus(TaskStatus.DONE);

        } else {

            task.setCompleted(false);

            if (task.getStatus() == null ||
                    task.getStatus() == TaskStatus.DONE) {

                task.setStatus(TaskStatus.IN_PROGRESS);
            }
        }

        return taskRepository.save(task);
    }

    // ================= COMPLETED TASKS =================

    public List<Task> getCompletedTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserAndCompleted(user, true);
    }

    // ================= PENDING TASKS =================

    public List<Task> getPendingTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserAndCompleted(user, false);
    }

    // ================= SEARCH =================

    public List<Task> searchTasks(
            String email,
            String keyword) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserAndTitleContainingIgnoreCase(
                user,
                keyword);
    }

    // ================= DUE TODAY =================

    public List<Task> dueToday(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserAndDueDate(
                user,
                LocalDate.now());
    }

    // ================= SORT =================

    public List<Task> sortByDueDate(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserOrderByDueDateAsc(user);
    }

    // ================= LATEST TASKS =================

    public List<Task> latestTasks(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return taskRepository.findByUserOrderByCreatedAtDesc(user);
    }

    // ================= PAGINATION =================

    public Page<Task> getTasksPage(
            String email,
            int page,
            int size) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Pageable pageable = PageRequest.of(page, size);

        return taskRepository.findByUser(user, pageable);
    }
}