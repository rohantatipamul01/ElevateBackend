package com.elevate.ElevateBackend.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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

    public OAuth2AuthenticationSuccessHandler(
            JwtUtil jwtUtil,
            UserRepository userRepository) {

        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        Object principal = authentication.getPrincipal();

        User user;

        if (principal instanceof CustomOAuthUser customUser) {

            user = customUser.getUser();

        } else if (principal instanceof OidcUser oidcUser) {

            String email = oidcUser.getEmail();

            user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("User not found: " + email));

        } else if (principal instanceof OAuth2User oauth2User) {

            String email = oauth2User.getAttribute("email");

            user = userRepository.findByEmail(email)
                    .orElseThrow(() ->
                            new RuntimeException("User not found: " + email));

        } else {

            throw new RuntimeException(
                    "Unsupported principal type: "
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

    private String encode(String value) {

        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8);
    }
}