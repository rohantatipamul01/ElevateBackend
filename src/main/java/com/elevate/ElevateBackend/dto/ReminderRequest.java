package com.elevate.ElevateBackend.dto;

import java.time.LocalDateTime;

import com.elevate.ElevateBackend.entity.Priority;

public class ReminderRequest {

    private String title;

    private String description;

    private LocalDateTime reminderTime;

    private Priority priority;

    public ReminderRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(LocalDateTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}