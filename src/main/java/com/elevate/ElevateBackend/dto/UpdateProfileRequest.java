package com.elevate.ElevateBackend.dto;

import java.time.LocalDate;

public class UpdateProfileRequest {

    private String fullName;
    private String username;
    private LocalDate dateOfBirth;

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}