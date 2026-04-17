package com.auth.app.controller;

import com.auth.app.model.User;
import com.auth.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")

// For development (React + Spring Boot)
@CrossOrigin(origins = {"http://localhost:5173"})
public class UserController {

    @Autowired
    private UserService userService;

    // =========================
    // REGISTER USER
    // =========================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);

            // Hide password before sending response
            savedUser.setPassword(null);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // LOGIN USER
    // =========================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String password = request.get("password");

            User user = userService.loginUser(username, password);

            // Hide password
            user.setPassword(null);

            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // GET USER BY ID
    // =========================
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            user.setPassword(null);
            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    // =========================
    // GET USER BY USERNAME
    // =========================
    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            user.setPassword(null);
            return ResponseEntity.ok(user);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }
}