package com.elevate.ElevateBackend.dto;

import java.time.LocalDate;

public class HabitResponse {

    private Long id;

    private String title;

    private String description;

    private String category;

    private String frequency;

    private Integer currentStreak;

    private Integer longestStreak;

    private Integer completedCount;

    private boolean active;

    private LocalDate lastCompletedDate;

    public HabitResponse() {
    }

    public HabitResponse(
            Long id,
            String title,
            String description,
            String category,
            String frequency,
            Integer currentStreak,
            Integer longestStreak,
            Integer completedCount,
            boolean active,
            LocalDate lastCompletedDate) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.frequency = frequency;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.completedCount = completedCount;
        this.active = active;
        this.lastCompletedDate = lastCompletedDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getFrequency() {
        return frequency;
    }

    public Integer getCurrentStreak() {
        return currentStreak;
    }

    public Integer getLongestStreak() {
        return longestStreak;
    }

    public Integer getCompletedCount() {
        return completedCount;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setCurrentStreak(Integer currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(Integer longestStreak) {
        this.longestStreak = longestStreak;
    }

    public void setCompletedCount(Integer completedCount) {
        this.completedCount = completedCount;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setLastCompletedDate(LocalDate lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }
}