package com.elevate.ElevateBackend.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.ReminderRequest;
import com.elevate.ElevateBackend.entity.Reminder;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.ReminderRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;
    private final UserRepository userRepository;

    public ReminderService(ReminderRepository reminderRepository,
                           UserRepository userRepository) {

        this.reminderRepository = reminderRepository;
        this.userRepository = userRepository;
    }

    public Reminder createReminder(ReminderRequest request,
                                   String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Reminder reminder = new Reminder();

        reminder.setTitle(request.getTitle());
        reminder.setDescription(request.getDescription());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setPriority(request.getPriority());

        reminder.setCompleted(false);
        reminder.setNotified(false);

        reminder.setUser(user);

        return reminderRepository.save(reminder);
    }

    public List<Reminder> getAllReminders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return reminderRepository.findByUser(user);
    }

    public Reminder getReminder(Long id,
                                String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return reminderRepository
                .findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Reminder Not Found"));
    }

    public Reminder updateReminder(Long id,
                                   ReminderRequest request,
                                   String email) {

        Reminder reminder = getReminder(id, email);

        reminder.setTitle(request.getTitle());
        reminder.setDescription(request.getDescription());
        reminder.setReminderTime(request.getReminderTime());
        reminder.setPriority(request.getPriority());

        return reminderRepository.save(reminder);
    }

    public void deleteReminder(Long id,
                               String email) {

        Reminder reminder = getReminder(id, email);

        reminderRepository.delete(reminder);
    }

    public Reminder markCompleted(Long id,
                                  String email) {

        Reminder reminder = getReminder(id, email);

        reminder.setCompleted(true);

        return reminderRepository.save(reminder);
    }

    public List<Reminder> getTodayReminders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        LocalDate today = LocalDate.now();

        return reminderRepository.findByUserAndReminderTimeBetween(
                user,
                today.atStartOfDay(),
                today.plusDays(1).atStartOfDay()
        );
    }

    public long totalReminders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return reminderRepository.countByUser(user);
    }

    public long completedReminders(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return reminderRepository.countByUserAndCompleted(user, true);
    }

}