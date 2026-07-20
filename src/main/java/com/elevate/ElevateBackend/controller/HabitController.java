package com.elevate.ElevateBackend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.elevate.ElevateBackend.dto.HabitDashboardResponse;
import com.elevate.ElevateBackend.dto.HabitRequest;
import com.elevate.ElevateBackend.entity.Habit;
import com.elevate.ElevateBackend.service.HabitService;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "http://localhost:5173")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public Habit createHabit(
            @RequestBody HabitRequest request,
            Principal principal) {

        return habitService.createHabit(
                request,
                principal.getName());
    }

    @GetMapping
    public List<Habit> getAllHabits(
            Principal principal) {

        return habitService.getAllHabits(
                principal.getName());
    }

    @GetMapping("/{id}")
    public Habit getHabit(
            @PathVariable Long id,
            Principal principal) {

        return habitService.getHabit(
                id,
                principal.getName());
    }

    @PutMapping("/{id}")
    public Habit updateHabit(
            @PathVariable Long id,
            @RequestBody HabitRequest request,
            Principal principal) {

        return habitService.updateHabit(
                id,
                request,
                principal.getName());
    }

    @DeleteMapping("/{id}")
    public String deleteHabit(
            @PathVariable Long id,
            Principal principal) {

        habitService.deleteHabit(
                id,
                principal.getName());

        return "Habit Deleted Successfully";
    }

    @PutMapping("/{id}/complete")
    public Habit completeHabit(
            @PathVariable Long id,
            Principal principal) {

        return habitService.completeHabit(
                id,
                principal.getName());
    }

    @GetMapping("/count")
    public long totalHabits(
            Principal principal) {

        return habitService.getTotalHabits(
                principal.getName());
    }

    @GetMapping("/active")
    public long activeHabits(
            Principal principal) {

        return habitService.getActiveHabits(
                principal.getName());
    }
    
    @GetMapping("/dashboard")
    public HabitDashboardResponse dashboard(
            Principal principal) {

        return habitService.getDashboardStats(
                principal.getName());
    }
}