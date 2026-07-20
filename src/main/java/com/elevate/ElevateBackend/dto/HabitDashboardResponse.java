package com.elevate.ElevateBackend.dto;

public class HabitDashboardResponse {

    private long totalHabits;
    private long activeHabits;
    private int currentStreak;
    private int longestStreak;
    private int completedCount;

    public HabitDashboardResponse() {
    }

    public HabitDashboardResponse(
            long totalHabits,
            long activeHabits,
            int currentStreak,
            int longestStreak,
            int completedCount) {

        this.totalHabits = totalHabits;
        this.activeHabits = activeHabits;
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
        this.completedCount = completedCount;
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

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

    public int getCompletedCount() {
        return completedCount;
    }

    public void setCompletedCount(int completedCount) {
        this.completedCount = completedCount;
    }
}