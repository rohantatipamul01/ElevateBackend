package com.elevate.ElevateBackend.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevate.ElevateBackend.dto.DashboardResponse;
import com.elevate.ElevateBackend.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(
            DashboardService dashboardService) {

        this.dashboardService = dashboardService;
    }

    @GetMapping
    public DashboardResponse getDashboard(
            Principal principal) {

        return dashboardService.getDashboard(
                principal.getName());

    }

}