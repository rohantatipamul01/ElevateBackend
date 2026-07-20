package com.elevate.ElevateBackend.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="streaks")
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int currentStreak;

    private int longestStreak;

    private LocalDate lastCompletedDate;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public Streak() {}

    public Long getId() {
        return id;
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

    public LocalDate getLastCompletedDate() {
        return lastCompletedDate;
    }

    public void setLastCompletedDate(LocalDate lastCompletedDate) {
        this.lastCompletedDate = lastCompletedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}