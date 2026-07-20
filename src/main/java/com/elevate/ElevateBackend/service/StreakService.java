package com.elevate.ElevateBackend.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.StreakResponse;
import com.elevate.ElevateBackend.entity.Streak;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.StreakRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class StreakService {

    private final StreakRepository streakRepository;
    private final UserRepository userRepository;

    public StreakService(StreakRepository streakRepository,
                         UserRepository userRepository) {

        this.streakRepository = streakRepository;
        this.userRepository = userRepository;
    }

    public StreakResponse getStreak(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        Streak streak = streakRepository.findByUser(user)
                .orElseGet(() -> {

                    Streak s = new Streak();

                    s.setUser(user);
                    s.setCurrentStreak(0);
                    s.setLongestStreak(0);

                    return streakRepository.save(s);

                });

        return new StreakResponse(

                streak.getCurrentStreak(),
                streak.getLongestStreak()

        );

    }

    public void updateStreak(User user) {

        Streak streak = streakRepository.findByUser(user)
                .orElseGet(() -> {
                    Streak s = new Streak();
                    s.setUser(user);
                    s.setCurrentStreak(0);
                    s.setLongestStreak(0);
                    return s;
                });

        LocalDate today = LocalDate.now();

        if (streak.getLastCompletedDate() == null) {

            streak.setCurrentStreak(1);

        } else if (streak.getLastCompletedDate().plusDays(1).equals(today)) {

            streak.setCurrentStreak(streak.getCurrentStreak() + 1);

        } else if (!streak.getLastCompletedDate().equals(today)) {

            streak.setCurrentStreak(1);
        }

        if (streak.getCurrentStreak() > streak.getLongestStreak()) {
            streak.setLongestStreak(streak.getCurrentStreak());
        }

        streak.setLastCompletedDate(today);

        streakRepository.save(streak);
    }

}