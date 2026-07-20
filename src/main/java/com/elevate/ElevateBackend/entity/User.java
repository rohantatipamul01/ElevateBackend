package com.elevate.ElevateBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private Role role;

    // ================= OAuth Provider =================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider provider = AuthProvider.LOCAL;

    // ================================================

    @Column(length = 15)
    private String phone;

    @Column(length = 500)
    private String bio;

    @Column(length = 100)
    private String location;

    @Column(name = "profile_image")
    private String profileImage;

    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> tasks = new ArrayList<>();

    @PrePersist
    public void prePersist() {

        createdAt = LocalDateTime.now();

        if (role == null) {
            role = Role.USER;
        }

        if (provider == null) {
            provider = AuthProvider.LOCAL;
        }
    }
}