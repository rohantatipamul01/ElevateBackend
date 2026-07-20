package com.elevate.ElevateBackend.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.CalendarEventResponse;
import com.elevate.ElevateBackend.entity.Reminder;
import com.elevate.ElevateBackend.entity.Task;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.ReminderRepository;
import com.elevate.ElevateBackend.repository.TaskRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class CalendarService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final ReminderRepository reminderRepository;

    public CalendarService(
            UserRepository userRepository,
            TaskRepository taskRepository,
            ReminderRepository reminderRepository) {

        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.reminderRepository = reminderRepository;
    }

    public List<CalendarEventResponse> getCalendarEvents(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        List<CalendarEventResponse> events = new ArrayList<>();

        // -------------------- TASKS --------------------

        List<Task> tasks = taskRepository.findByUser(user);

        for (Task task : tasks) {

            if (task.getDueDate() == null) {
                continue;
            }

            CalendarEventResponse event =
                    new CalendarEventResponse();

            event.setId(task.getId());
            event.setType("TASK");
            event.setTitle(task.getTitle());
            event.setDescription(task.getDescription());
            event.setDate(task.getDueDate());
            event.setPriority(task.getPriority());
            event.setCompleted(task.isCompleted());

            events.add(event);
        }

        // -------------------- REMINDERS --------------------

        List<Reminder> reminders =
                reminderRepository.findByUser(user);

        for (Reminder reminder : reminders) {

            CalendarEventResponse event =
                    new CalendarEventResponse();

            event.setId(reminder.getId());
            event.setType("REMINDER");
            event.setTitle(reminder.getTitle());
            event.setDescription(reminder.getDescription());
            event.setDate(
                    reminder.getReminderTime()
                            .toLocalDate());

            event.setPriority(reminder.getPriority());
            event.setCompleted(reminder.isCompleted());

            events.add(event);
        }

        events.sort(
                Comparator.comparing(
                        CalendarEventResponse::getDate));

        return events;
    }
}