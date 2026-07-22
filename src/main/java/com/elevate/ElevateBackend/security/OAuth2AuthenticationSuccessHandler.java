package com.elevate.ElevateBackend.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.elevate.ElevateBackend.entity.AuthProvider;
import com.elevate.ElevateBackend.entity.Role;
import com.elevate.ElevateBackend.entity.User;
import com.elevate.ElevateBackend.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuth2AuthenticationSuccessHandler(
            JwtUtil jwtUtil,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        User user;

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomOAuthUser customOAuthUser) {

            user = customOAuthUser.getUser();

        } else if (principal instanceof OidcUser oidcUser) {

            user = getOrCreateUser(
                    oidcUser.getEmail(),
                    oidcUser.getFullName(),
                    oidcUser.getPicture());

        } else if (principal instanceof OAuth2User oauth2User) {

            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            String picture = oauth2User.getAttribute("picture");

            user = getOrCreateUser(email, name, picture);

        } else {

            throw new RuntimeException(
                    "Unsupported principal type : "
                            + principal.getClass().getName());
        }

        String token = jwtUtil.generateToken(user.getEmail());

        String redirectUrl =
                "http://localhost:5173/oauth/success"
                        + "?token=" + encode(token)
                        + "&id=" + user.getId()
                        + "&fullName=" + encode(user.getFullName())
                        + "&username=" + encode(user.getUsername())
                        + "&email=" + encode(user.getEmail())
                        + "&image=" + encode(
                                user.getProfileImage() == null
                                        ? ""
                                        : user.getProfileImage());

        getRedirectStrategy().sendRedirect(
                request,
                response,
                redirectUrl);
    }

    private User getOrCreateUser(
            String email,
            String fullName,
            String picture) {

        return userRepository.findByEmail(email)
                .orElseGet(() -> {

                    User user = new User();

                    user.setEmail(email);

                    user.setFullName(
                            fullName == null || fullName.isBlank()
                                    ? "User"
                                    : fullName);

                    String username =
                            email.substring(0, email.indexOf("@"));

                    String finalUsername = username;
                    int i = 1;

                    while (userRepository.findByUsername(finalUsername).isPresent()) {
                        finalUsername = username + i;
                        i++;
                    }

                    user.setUsername(finalUsername);

                    user.setPassword(
                            passwordEncoder.encode(
                                    UUID.randomUUID().toString()));

                    user.setRole(Role.USER);

                    user.setProvider(AuthProvider.GOOGLE);

                    user.setProfileImage(picture);

                    return userRepository.save(user);
                });
    }

    private String encode(String value) {

        if (value == null) {
            return "";
        }

        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8);
    }
}