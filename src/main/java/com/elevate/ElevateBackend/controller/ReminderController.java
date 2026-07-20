package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.ReminderRequest;
import com.elevate.ElevateBackend.entity.Reminder;
import com.elevate.ElevateBackend.service.ReminderService;

@RestController
@RequestMapping("/api/reminders")
@CrossOrigin(origins = "http://localhost:5173")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping
    public Reminder createReminder(
            @RequestBody ReminderRequest request,
            Principal principal) {

        return reminderService.createReminder(
                request,
                principal.getName());
    }

    @GetMapping
    public List<Reminder> getAllReminders(
            Principal principal) {

        return reminderService.getAllReminders(
                principal.getName());
    }

    @GetMapping("/{id}")
    public Reminder getReminder(
            @PathVariable Long id,
            Principal principal) {

        return reminderService.getReminder(
                id,
                principal.getName());
    }

    @PutMapping("/{id}")
    public Reminder updateReminder(
            @PathVariable Long id,
            @RequestBody ReminderRequest request,
            Principal principal) {

        return reminderService.updateReminder(
                id,
                request,
                principal.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteReminder(
            @PathVariable Long id,
            Principal principal) {

        reminderService.deleteReminder(
                id,
                principal.getName());

        return "Reminder Deleted Successfully";
    }

    @PutMapping("/{id}/complete")
    public Reminder completeReminder(
            @PathVariable Long id,
            Principal principal) {

        return reminderService.markCompleted(
                id,
                principal.getName());
    }

    @GetMapping("/today")
    public List<Reminder> todayReminders(
            Principal principal) {

        return reminderService.getTodayReminders(
                principal.getName());
    }

    @GetMapping("/count")
    public long totalReminders(
            Principal principal) {

        return reminderService.totalReminders(
                principal.getName());
    }

    @GetMapping("/completed")
    public long completedReminders(
            Principal principal) {

        return reminderService.completedReminders(
                principal.getName());
    }
}