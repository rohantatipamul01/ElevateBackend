package com.elevate.ElevateBackend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.HabitDashboardResponse;
import com.elevate.ElevateBackend.dto.HabitRequest;
import com.elevate.ElevateBackend.entity.Habit;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.HabitRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(HabitRepository habitRepository,
                        UserRepository userRepository) {

        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public Habit createHabit(HabitRequest request, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        Habit habit = new Habit();

        habit.setTitle(request.getTitle());
        habit.setDescription(request.getDescription());
        habit.setCategory(request.getCategory());
        habit.setFrequency(request.getFrequency());

        habit.setCurrentStreak(0);
        habit.setLongestStreak(0);
        habit.setCompletedCount(0);
        habit.setActive(true);

        habit.setUser(user);

        return habitRepository.save(habit);
    }

    public List<Habit> getAllHabits(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return habitRepository.findByUser(user);
    }

    public Habit getHabit(Long id, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return habitRepository.findByIdAndUser(id, user)
                .orElseThrow(() ->
                        new RuntimeException("Habit Not Found"));
    }

    public Habit updateHabit(Long id,
                             HabitRequest request,
                             String email) {

        Habit habit = getHabit(id, email);

        habit.setTitle(request.getTitle());
        habit.setDescription(request.getDescription());
        habit.setCategory(request.getCategory());
        habit.setFrequency(request.getFrequency());

        return habitRepository.save(habit);
    }

    public void deleteHabit(Long id, String email) {

        Habit habit = getHabit(id, email);

        habitRepository.delete(habit);
    }

    public Habit completeHabit(Long id, String email) {

        Habit habit = getHabit(id, email);

        LocalDate today = LocalDate.now();

        if (habit.getLastCompletedDate() == null) {

            habit.setCurrentStreak(1);

        } else if (habit.getLastCompletedDate().plusDays(1).equals(today)) {

            habit.setCurrentStreak(
                    habit.getCurrentStreak() + 1);

        } else if (!habit.getLastCompletedDate().equals(today)) {

            habit.setCurrentStreak(1);
        }

        if (habit.getCurrentStreak() > habit.getLongestStreak()) {

            habit.setLongestStreak(
                    habit.getCurrentStreak());
        }

        habit.setCompletedCount(
                habit.getCompletedCount() + 1);

        habit.setLastCompletedDate(today);

        return habitRepository.save(habit);
    }

    public long getTotalHabits(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return habitRepository.countByUser(user);
    }

    public long getActiveHabits(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        return habitRepository.countByUserAndActive(user, true);
    }
    public HabitDashboardResponse getDashboardStats(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));

        List<Habit> habits = habitRepository.findByUser(user);

        long totalHabits = habits.size();

        long activeHabits = habits.stream()
                .filter(Habit::isActive)
                .count();

        int currentStreak = habits.stream()
                .mapToInt(Habit::getCurrentStreak)
                .max()
                .orElse(0);

        int longestStreak = habits.stream()
                .mapToInt(Habit::getLongestStreak)
                .max()
                .orElse(0);

        int completedCount = habits.stream()
                .mapToInt(Habit::getCompletedCount)
                .sum();

        return new HabitDashboardResponse(
                totalHabits,
                activeHabits,
                currentStreak,
                longestStreak,
                completedCount
        );
    }
}