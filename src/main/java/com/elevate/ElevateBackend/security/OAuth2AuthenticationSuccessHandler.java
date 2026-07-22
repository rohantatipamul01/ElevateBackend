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

        try {

            System.out.println("========================================");
            System.out.println("OAUTH2 SUCCESS HANDLER STARTED");
            System.out.println("========================================");

            Object principal = authentication.getPrincipal();

            System.out.println("Principal Class : "
                    + principal.getClass().getName());

            User user;

            if (principal instanceof CustomOAuthUser customUser) {

                user = customUser.getUser();

                System.out.println("Authenticated using CustomOAuthUser");

            } else if (principal instanceof OidcUser oidcUser) {

                String email = oidcUser.getEmail();

                System.out.println("OIDC Email : " + email);

                user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found : " + email));

            } else if (principal instanceof OAuth2User oauth2User) {

                String email = oauth2User.getAttribute("email");

                System.out.println("OAuth2 Email : " + email);

                user = userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found : " + email));

            } else {

                throw new RuntimeException(
                        "Unsupported Principal : "
                                + principal.getClass().getName());
            }

            System.out.println("----------------------------------------");
            System.out.println("User Found Successfully");
            System.out.println("ID        : " + user.getId());
            System.out.println("Full Name : " + user.getFullName());
            System.out.println("Username  : " + user.getUsername());
            System.out.println("Email     : " + user.getEmail());
            System.out.println("----------------------------------------");

            String token = jwtUtil.generateToken(user.getEmail());

            System.out.println("JWT Generated Successfully");

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

            System.out.println("Redirect URL :");
            System.out.println(redirectUrl);

            System.out.println("Redirecting...");

            getRedirectStrategy().sendRedirect(
                    request,
                    response,
                    redirectUrl);

            System.out.println("Redirect Completed");

        } catch (Exception e) {

            System.out.println("========================================");
            System.out.println("OAUTH2 ERROR OCCURRED");
            System.out.println("========================================");

            e.printStackTrace();

            System.out.println("========================================");

            throw e;
        }
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