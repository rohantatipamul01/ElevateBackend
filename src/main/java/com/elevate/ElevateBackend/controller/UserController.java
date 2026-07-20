package com.elevate.ElevateBackend.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.*;
import com.elevate.ElevateBackend.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public UserProfileResponse profile(
            Principal principal){

        return userService.getProfile(
                principal.getName());
    }

    @PutMapping("/profile")
    public String updateProfile(
            @RequestBody UpdateProfileRequest request,
            Principal principal){

        return userService.updateProfile(
                principal.getName(),
                request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal principal){

        return userService.changePassword(
                principal.getName(),
                request);
    }
    
    @PostMapping("/profile-picture")
    public String uploadPicture(
            @RequestParam("file") MultipartFile file,
            Principal principal)
            throws Exception {

        return userService.uploadProfilePicture(
                principal.getName(),
                file);

    }

}