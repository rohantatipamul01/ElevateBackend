package com.elevate.ElevateBackend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.dto.AchievementResponse;
import com.elevate.ElevateBackend.entity.Achievement;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.AchievementRepository;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public AchievementService(AchievementRepository achievementRepository,
                              UserRepository userRepository) {

        this.achievementRepository = achievementRepository;
        this.userRepository = userRepository;
    }

    public List<AchievementResponse> getAchievements(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow();

        return achievementRepository.findByUser(user)
                .stream()
                .map(a -> new AchievementResponse(
                        a.getTitle(),
                        a.getDescription(),
                        a.getBadge(),
                        a.isUnlocked()))
                .collect(Collectors.toList());

    }

	public void checkAchievements(User user) {
		// TODO Auto-generated method stub
		
	}

}