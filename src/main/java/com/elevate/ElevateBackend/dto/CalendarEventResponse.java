package com.elevate.ElevateBackend.dto;

import java.time.LocalDate;

import com.elevate.ElevateBackend.entity.Priority;

public class CalendarEventResponse {

    private Long id;

    private String type;

    private String title;

    private String description;

    private LocalDate date;

    private Priority priority;

    private boolean completed;

    public CalendarEventResponse() {
    }

    public CalendarEventResponse(
            Long id,
            String type,
            String title,
            String description,
            LocalDate date,
            Priority priority,
            boolean completed) {

        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}