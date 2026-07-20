package com.elevate.ElevateBackend.dto;

public class AchievementResponse {

    private String title;
    private String description;
    private String badge;
    private boolean unlocked;

    public AchievementResponse() {
    }

    public AchievementResponse(String title,
                               String description,
                               String badge,
                               boolean unlocked) {

        this.title = title;
        this.description = description;
        this.badge = badge;
        this.unlocked = unlocked;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getBadge() {
        return badge;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }
}