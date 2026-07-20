package com.elevate.ElevateBackend.dto;

public class DashboardResponse {

    private int totalTasks;

    private int completedTasks;

    private int pendingTasks;

    private double productivity;

    private int dueToday;

    private int overdue;

    private int streak;

    public DashboardResponse() {
    }

    public DashboardResponse(
            int totalTasks,
            int completedTasks,
            int pendingTasks,
            double productivity,
            int dueToday,
            int overdue,
            int streak) {

        this.totalTasks = totalTasks;
        this.completedTasks = completedTasks;
        this.pendingTasks = pendingTasks;
        this.productivity = productivity;
        this.dueToday = dueToday;
        this.overdue = overdue;
        this.streak = streak;
    }

    public int getTotalTasks() {
        return totalTasks;
    }

    public void setTotalTasks(int totalTasks) {
        this.totalTasks = totalTasks;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }

    public double getProductivity() {
        return productivity;
    }

    public void setProductivity(double productivity) {
        this.productivity = productivity;
    }

    public int getDueToday() {
        return dueToday;
    }

    public void setDueToday(int dueToday) {
        this.dueToday = dueToday;
    }

    public int getOverdue() {
        return overdue;
    }

    public void setOverdue(int overdue) {
        this.overdue = overdue;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}