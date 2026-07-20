package com.elevate.ElevateBackend.dto;

import java.time.LocalDateTime;

public class ProfileResponse {

    private Long id;

    private String fullName;

    private String username;

    private String email;

    private String phone;

    private String bio;

    private String location;

    private String profileImage;

    private LocalDateTime createdAt;

    public ProfileResponse() {
    }

    public ProfileResponse(
            Long id,
            String fullName,
            String username,
            String email,
            String phone,
            String bio,
            String location,
            String profileImage,
            LocalDateTime createdAt) {

        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.bio = bio;
        this.location = location;
        this.profileImage = profileImage;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}