package com.corporate.portal.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// DTO for login request body
@Data
@NoArgsConstructor
@AllArgsConstructor
class LoginRequest {
    private String username;
    private String password;
}

// DTO for login response body
@Data
@NoArgsConstructor
@AllArgsConstructor
class LoginResponse {
    private String token;
    private String username;
    private String role;
    private String message;
}
