package com.corporate.portal.controller;

import com.corporate.portal.entity.User;
import com.corporate.portal.repository.UserRepository;
import com.corporate.portal.service.CustomUserDetailsService;
import com.corporate.portal.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    /**
     * POST /api/auth/login
     * Authenticates user and returns a JWT token.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Authenticate credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid username or password"));
        }

        // Load user details and generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String token = jwtUtil.generateToken(userDetails);

        // Get role from DB for response
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "role", user.getRole(),
                "message", "Login successful"
        ));
    }

    // ---- Inner DTOs ----

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LoginRequest {
        private String username;
        private String password;
		public String getUsername() {
			// TODO Auto-generated method stub
			return null;
		}
		public Object getPassword() {
			// TODO Auto-generated method stub
			return null;
		}
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class LoginResponse {
        private String token;
        private String username;
        private String role;
        private String message;
    }
}
