package com.elevate.ElevateBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String message;
    private UserResponse user;

}