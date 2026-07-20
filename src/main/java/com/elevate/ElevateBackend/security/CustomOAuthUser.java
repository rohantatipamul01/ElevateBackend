package com.elevate.ElevateBackend.security;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.elevate.ElevateBackend.entity.User;

public class CustomOAuthUser implements OAuth2User {

    private final User user;

    private final Map<String, Object> attributes;

    public CustomOAuthUser(
            User user,
            Map<String, Object> attributes) {

        this.user = user;
        this.attributes = attributes;

    }

    public User getUser() {
        return user;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(
                () -> user.getRole().name());

    }

    @Override
    public String getName() {

        return user.getEmail();

    }

}