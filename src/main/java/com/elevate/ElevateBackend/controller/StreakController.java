package com.elevate.ElevateBackend.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevate.ElevateBackend.dto.StreakResponse;
import com.elevate.ElevateBackend.service.StreakService;

@RestController
@RequestMapping("/api/streak")
public class StreakController {

    private final StreakService streakService;

    public StreakController(StreakService streakService){

        this.streakService = streakService;
    }

    @GetMapping
    public StreakResponse getStreak(
            Principal principal){

        return streakService.getStreak(
                principal.getName());

    }

}