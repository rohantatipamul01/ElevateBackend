package com.elevate.ElevateBackend.dto;

public class StreakResponse {

    private int currentStreak;

    private int longestStreak;

    public StreakResponse() {}

    public StreakResponse(int currentStreak, int longestStreak) {
        this.currentStreak = currentStreak;
        this.longestStreak = longestStreak;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public void setLongestStreak(int longestStreak) {
        this.longestStreak = longestStreak;
    }

}