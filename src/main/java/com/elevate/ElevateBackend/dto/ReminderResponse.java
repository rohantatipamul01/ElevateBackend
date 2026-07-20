package com.elevate.ElevateBackend.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReminderResponse {

    private Long id;

    private String message;

    private LocalDate reminderDate;

    private LocalTime reminderTime;

    private boolean completed;

    public ReminderResponse() {}

    public ReminderResponse(Long id,
                            String message,
                            LocalDate reminderDate,
                            LocalTime reminderTime,
                            boolean completed) {

        this.id = id;
        this.message = message;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.completed = completed;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public LocalDate getReminderDate() { return reminderDate; }
    public LocalTime getReminderTime() { return reminderTime; }
    public boolean isCompleted() { return completed; }
}