package com.elevate.ElevateBackend.dto;

public class AnalyticsResponse {

    private long totalTasks;
    private long completedTasks;

    private long totalHabits;
    private long activeHabits;

    private long totalReminders;
    private long completedReminders;

    private double taskCompletionRate;
    private double reminderCompletionRate;
    private double productivityScore;

    public AnalyticsResponse() {
    }

    public AnalyticsResponse(
            long totalTasks,
            long completedTasks,
            long totalHabits,
            long activeHabits,
            long totalReminders,
            long completedReminders,
            double taskCompletionRate,
            double reminderCompletionRate,
            double productivityScore) {

        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.totalHabits = totalHabits;
        this.activeHabits = activeHabits;
        this.totalReminders = totalReminders;
        this.completedReminders = completedReminders;
        this.taskCompletionRate = taskCompletionRate;
        this.reminderCompletionRate = reminderCompletionRate;
        this.productivityScore = productivityScore;
    }

    public long getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(long totalTasks) {
        this.totalTasks = totalTasks;
    }

    public long getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(long completedTasks) {
        this.completedTasks = completedTasks;
    }

    public long getTotalHabits() {
        return totalHabits;
    }

    public void setTotalHabits(long totalHabits) {
        this.totalHabits = totalHabits;
    }

    public long getActiveHabits() {
        return activeHabits;
    }

    public void setActiveHabits(long activeHabits) {
        this.activeHabits = activeHabits;
    }

    public long getTotalReminders() {
        return totalReminders;
    }

    public void setTotalReminders(long totalReminders) {
        this.totalReminders = totalReminders;
    }

    public long getCompletedReminders() {
        return completedReminders;
    }

    public void setCompletedReminders(long completedReminders) {
        this.completedReminders = completedReminders;
    }

    public double getTaskCompletionRate() {
        return taskCompletionRate;
    }

    public void setTaskCompletionRate(double taskCompletionRate) {
        this.taskCompletionRate = taskCompletionRate;
    }

    public double getReminderCompletionRate() {
        return reminderCompletionRate;
    }

    public void setReminderCompletionRate(double reminderCompletionRate) {
        this.reminderCompletionRate = reminderCompletionRate;
    }

    public double getProductivityScore() {
        return productivityScore;
    }

    public void setProductivityScore(double productivityScore) {
        this.productivityScore = productivityScore;
    }
}