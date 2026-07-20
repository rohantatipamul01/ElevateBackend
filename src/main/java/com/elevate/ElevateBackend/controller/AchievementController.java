package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.AchievementResponse;
import com.elevate.ElevateBackend.service.AchievementService;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    private final AchievementService achievementService;

    public AchievementController(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @GetMapping
    public List<AchievementResponse> getAchievements(
            Principal principal){

        return achievementService.getAchievements(
                principal.getName());
    }

}