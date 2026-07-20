package com.elevate.ElevateBackend.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.elevate.ElevateBackend.security.CustomOAuth2UserService;
import com.elevate.ElevateBackend.security.JwtAuthenticationFilter;
import com.elevate.ElevateBackend.security.OAuth2AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler successHandler;

    public SecurityConfig(
            JwtAuthenticationFilter jwtFilter,
            CustomOAuth2UserService customOAuth2UserService,
            OAuth2AuthenticationSuccessHandler successHandler) {

        this.jwtFilter = jwtFilter;
        this.customOAuth2UserService = customOAuth2UserService;
        this.successHandler = successHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()))

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/api/auth/**",
                                "/oauth2/**",
                                "/login/**",
                                "/uploads/**")
                        .permitAll()

                        .anyRequest()
                        .authenticated())

                .oauth2Login(oauth -> oauth

                        .userInfoEndpoint(userInfo ->

                                userInfo.userService(
                                        customOAuth2UserService))

                        .successHandler(successHandler))

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173"));

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"));

        configuration.setAllowedHeaders(
                List.of("*"));

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration);

        return source;
    }
}