package com.elevate.ElevateBackend.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.elevate.ElevateBackend.dto.PasswordRequest;
import com.elevate.ElevateBackend.dto.ProfileRequest;
import com.elevate.ElevateBackend.dto.ProfileResponse;
import com.elevate.ElevateBackend.service.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profile")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // ================= GET PROFILE =================

    @GetMapping
    public ResponseEntity<ProfileResponse> getProfile(
            Principal principal) {

        return ResponseEntity.ok(
                profileService.getProfile(
                        principal.getName()
                )
        );
    }

    // ================= UPDATE PROFILE =================

    @PutMapping
    public ResponseEntity<ProfileResponse> updateProfile(
            Principal principal,
            @Valid @RequestBody ProfileRequest request) {

        return ResponseEntity.ok(
                profileService.updateProfile(
                        principal.getName(),
                        request
                )
        );
    }

    // ================= CHANGE PASSWORD =================

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            Principal principal,
            @Valid @RequestBody PasswordRequest request) {

        return ResponseEntity.ok(
                profileService.changePassword(
                        principal.getName(),
                        request
                )
        );
    }

    // ================= UPLOAD PROFILE IMAGE =================

    @PostMapping(
            value = "/image",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<ProfileResponse> uploadProfileImage(

            Principal principal,

            @RequestParam("file")
            MultipartFile file

    ) {

        return ResponseEntity.ok(

                profileService.uploadProfileImage(

                        principal.getName(),

                        file

                )

        );

    }

}