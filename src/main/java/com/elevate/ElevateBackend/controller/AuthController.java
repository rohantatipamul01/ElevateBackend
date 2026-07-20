package com.elevate.ElevateBackend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.JwtResponse;
import com.elevate.ElevateBackend.dto.LoginRequest;
import com.elevate.ElevateBackend.dto.SignupRequest;
import com.elevate.ElevateBackend.dto.UserResponse;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.security.JwtUtil;
import com.elevate.ElevateBackend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          JwtUtil jwtUtil) {

        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignupRequest request) {

        return new ResponseEntity<>(
                userService.registerUser(request),
                HttpStatus.CREATED
        );
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {

        System.out.println("========== LOGIN API HIT ==========");

        User user = userService.login(request);

        String token = jwtUtil.generateToken(user.getEmail());

        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getProfileImage()
        );

        return ResponseEntity.ok(
                new JwtResponse(
                        token,
                        "Login Successful",
                        userResponse
                )
        );
    }
}