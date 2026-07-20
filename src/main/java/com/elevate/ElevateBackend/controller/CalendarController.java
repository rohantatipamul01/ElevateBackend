package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elevate.ElevateBackend.dto.CalendarEventResponse;
import com.elevate.ElevateBackend.service.CalendarService;

@RestController
@RequestMapping("/api/calendar")
@CrossOrigin(origins = "http://localhost:5173")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping
    public List<CalendarEventResponse> getCalendarEvents(
            Principal principal) {

        return calendarService.getCalendarEvents(
                principal.getName());
    }
}