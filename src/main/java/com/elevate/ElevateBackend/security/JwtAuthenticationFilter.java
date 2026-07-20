package com.elevate.ElevateBackend.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            JwtUtil jwtUtil,
            CustomUserDetailsService userDetailsService) {

        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Skip JWT validation for OAuth endpoints and public endpoints.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {

        String path = request.getServletPath();

        return path.startsWith("/oauth2/")
                || path.startsWith("/login/oauth2/")
                || path.startsWith("/api/auth/")
                || path.startsWith("/uploads/")
                || path.startsWith("/error");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("\n================ JWT FILTER ================");
        System.out.println("Request URI : " + request.getRequestURI());

        String header = request.getHeader("Authorization");

        System.out.println("Authorization Header : " + header);

        if (header == null || !header.startsWith("Bearer ")) {

            System.out.println("No Bearer Token Found");
            System.out.println("===========================================\n");

            filterChain.doFilter(request, response);
            return;
        }

        try {

            String token = header.substring(7);

            System.out.println("Token : " + token);

            if (jwtUtil.validateToken(token)) {

                String email = jwtUtil.extractEmail(token);

                System.out.println("Email From Token : " + email);

                UserDetails userDetails =
                        userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request));

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authentication);

                System.out.println("Authentication Set Successfully");

            } else {

                System.out.println("Invalid JWT Token");

            }

        } catch (Exception e) {

            System.out.println("JWT Exception : " + e.getMessage());
            e.printStackTrace();

        }

        System.out.println("===========================================\n");

        filterChain.doFilter(request, response);
    }
}