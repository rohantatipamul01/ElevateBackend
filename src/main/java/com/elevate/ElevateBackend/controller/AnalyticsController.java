package com.elevate.ElevateBackend.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevate.ElevateBackend.dto.AnalyticsResponse;
import com.elevate.ElevateBackend.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:5173")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(
            AnalyticsService analyticsService) {

        this.analyticsService = analyticsService;
    }

    @GetMapping
    public AnalyticsResponse analytics(
            Principal principal) {

        return analyticsService.getAnalytics(
                principal.getName());
    }

}