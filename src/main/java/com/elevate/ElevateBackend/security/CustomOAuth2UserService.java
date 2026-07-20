package com.elevate.ElevateBackend.security;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.elevate.ElevateBackend.entity.AuthProvider;
import com.elevate.ElevateBackend.entity.Role;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.UserRepository;

@Service
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        OAuth2User oauthUser = new DefaultOAuth2UserService()
                .loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        User user = getOrCreateUser(
                oauthUser,
                registrationId);

        return new CustomOAuthUser(
                user,
                oauthUser.getAttributes());
    }

    private User getOrCreateUser(
            OAuth2User oauthUser,
            String provider) {

        String email = extractEmail(oauthUser, provider);

        User existingUser = userRepository
                .findByEmail(email)
                .orElse(null);

        if (existingUser != null) {

            if (existingUser.getProvider() == AuthProvider.LOCAL &&
                    !"LOCAL".equalsIgnoreCase(provider)) {

                existingUser.setProvider(
                        AuthProvider.valueOf(provider.toUpperCase()));

                userRepository.save(existingUser);
            }

            return existingUser;
        }

        User user = new User();

        user.setEmail(email);

        user.setFullName(extractName(oauthUser));

        user.setUsername(generateUsername(email));

        user.setPassword(
                passwordEncoder.encode(
                        UUID.randomUUID().toString()));

        user.setRole(Role.USER);

        user.setProvider(
                AuthProvider.valueOf(provider.toUpperCase()));

        user.setProfileImage(
                extractPicture(oauthUser, provider));

        return userRepository.save(user);
    }

    private String extractEmail(
            OAuth2User oauthUser,
            String provider) {

        String email = oauthUser.getAttribute("email");

        if (email == null) {

            throw new OAuth2AuthenticationException(
                    "Email not available from "
                            + provider);
        }

        return email;
    }

    private String extractName(
            OAuth2User oauthUser) {

        String name = oauthUser.getAttribute("name");

        if (name == null || name.isBlank()) {

            name = "User";
        }

        return name;
    }

    private String extractPicture(
            OAuth2User oauthUser,
            String provider) {

        if ("google".equalsIgnoreCase(provider)) {

            return oauthUser.getAttribute("picture");
        }

        if ("github".equalsIgnoreCase(provider)) {

            return oauthUser.getAttribute("avatar_url");
        }

        return null;
    }

    private String generateUsername(
            String email) {

        String base =
                email.substring(0, email.indexOf("@"));

        String username = base;

        int i = 1;

        while (userRepository
                .findByUsername(username)
                .isPresent()) {

            username = base + i;

            i++;
        }

        return username;
    }

}