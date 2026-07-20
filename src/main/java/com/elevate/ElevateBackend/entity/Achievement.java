package com.elevate.ElevateBackend.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name="achievements")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private String badge;

    private boolean unlocked;

    private LocalDate unlockedDate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public Achievement() {
    }

    public Long getId() {
        return id;
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

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public LocalDate getUnlockedDate() {
        return unlockedDate;
    }

    public void setUnlockedDate(LocalDate unlockedDate) {
        this.unlockedDate = unlockedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}