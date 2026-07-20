package com.elevate.ElevateBackend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.elevate.ElevateBackend.dto.PasswordRequest;
import com.elevate.ElevateBackend.dto.ProfileRequest;
import com.elevate.ElevateBackend.dto.ProfileResponse;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    public ProfileService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            FileStorageService fileStorageService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }

    // ================= GET PROFILE =================

    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return mapToResponse(user);
    }

    // ================= UPDATE PROFILE =================

    public ProfileResponse updateProfile(
            String email,
            ProfileRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.findByUsername(request.getUsername())
                .ifPresent(existingUser -> {

                    if (!existingUser.getId().equals(user.getId())) {

                        throw new RuntimeException(
                                "Username already exists");

                    }

                });

        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setLocation(request.getLocation());
        user.setBio(request.getBio());
        user.setDateOfBirth(request.getDateOfBirth());

        userRepository.save(user);

        return mapToResponse(user);
    }

    // ================= CHANGE PASSWORD =================

    public String changePassword(
            String email,
            PasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Current password is incorrect");
        }

        if (!request.getNewPassword()
                .equals(request.getConfirmPassword())) {

            throw new RuntimeException(
                    "New password and Confirm password do not match");
        }

        if (request.getNewPassword().length() < 6) {

            throw new RuntimeException(
                    "Password must be at least 6 characters");
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully.";
    }

    // ================= UPLOAD PROFILE IMAGE =================

    public ProfileResponse uploadProfileImage(
            String email,
            MultipartFile file) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // Delete previous image
        if (user.getProfileImage() != null &&
                !user.getProfileImage().isBlank()) {

            fileStorageService.deleteImage(
                    user.getProfileImage());
        }

        String imagePath =
                fileStorageService.uploadProfileImage(file);

        user.setProfileImage(imagePath);

        userRepository.save(user);

        return mapToResponse(user);
    }

    // ================= PRIVATE MAPPER =================

    private ProfileResponse mapToResponse(User user) {

        return new ProfileResponse(

                user.getId(),

                user.getFullName(),

                user.getUsername(),

                user.getEmail(),

                user.getPhone(),

                user.getBio(),

                user.getLocation(),

                user.getProfileImage(),

                user.getCreatedAt()

        );

    }

}