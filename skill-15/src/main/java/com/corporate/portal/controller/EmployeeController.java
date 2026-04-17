package com.corporate.portal.controller;

import com.corporate.portal.entity.User;
import com.corporate.portal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/employee")
@PreAuthorize("hasRole('EMPLOYEE')")  // All endpoints require EMPLOYEE role
public class EmployeeController {

    @Autowired
    private UserRepository userRepository;

    /**
     * GET /api/employee/profile
     * Returns the authenticated employee's profile — EMPLOYEE only
     */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        // Get the currently authenticated user's username from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();

        User user = userRepository.findByUsername(currentUsername)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Profile not found"));
        }

        return ResponseEntity.ok(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "role", user.getRole(),
                "message", "Welcome, " + user.getUsername() + "! Here is your profile."
        ));
    }

    /**
     * GET /api/employee/dashboard
     * Employee dashboard — EMPLOYEE only
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "message", "Welcome to the Employee Dashboard",
                "loggedInAs", auth.getName(),
                "authorities", auth.getAuthorities().toString()
        ));
    }
}
