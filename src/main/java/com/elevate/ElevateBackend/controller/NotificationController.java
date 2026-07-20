package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevate.ElevateBackend.dto.NotificationResponse;
import com.elevate.ElevateBackend.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(
            NotificationService notificationService) {

        this.notificationService = notificationService;
    }

    @GetMapping
    public List<NotificationResponse> notifications(
            Principal principal) {

        return notificationService.getNotifications(
                principal.getName());

    }

}