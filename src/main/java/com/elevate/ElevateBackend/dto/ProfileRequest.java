package com.elevate.ElevateBackend.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfileRequest {

    @NotBlank(message = "Full name is required")
    @Size(max = 100)
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30)
    private String username;

    @Pattern(
            regexp = "^$|^[0-9]{10}$",
            message = "Phone number must contain exactly 10 digits"
    )
    private String phone;

    @Size(max = 100)
    private String location;

    @Size(max = 500)
    private String bio;

    private LocalDate dateOfBirth;

    public ProfileRequest() {
    }

    public ProfileRequest(
            String fullName,
            String username,
            String phone,
            String location,
            String bio,
            LocalDate dateOfBirth) {

        this.fullName = fullName;
        this.username = username;
        this.phone = phone;
        this.location = location;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}