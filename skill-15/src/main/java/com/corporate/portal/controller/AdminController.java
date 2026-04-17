package com.corporate.portal.controller;

import com.corporate.portal.entity.User;
import com.corporate.portal.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")   // All endpoints in this controller require ADMIN role
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * GET /api/admin/users
     * List all users (admin utility endpoint)
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(Map.of(
                "totalUsers", users.size(),
                "users", users.stream().map(u -> Map.of(
                        "id", u.getId(),
                        "username", u.getUsername(),
                        "role", u.getRole()
                )).toList()
        ));
    }

    /**
     * POST /api/admin/add
     * Add a new employee record — ADMIN only
     */
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Username already exists: " + request.getUsername()));
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole() != null ? ((String) request.getRole()).toUpperCase() : "EMPLOYEE");
        userRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of(
                        "message", "Employee added successfully",
                        "username", newUser.getUsername(),
                        "role", newUser.getRole()
                ));
    }

    /**
     * DELETE /api/admin/delete/{username}
     * Delete an employee record — ADMIN only
     */
    @DeleteMapping("/delete/{username}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "User not found: " + username));
        }

        userRepository.delete(user);
        return ResponseEntity.ok(Map.of(
                "message", "Employee deleted successfully",
                "deletedUsername", username
        ));
    }

    // ---- Inner DTO ----

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class EmployeeRequest {
        private String username;
        private String password;
        private String role;
		public Object getRole() {
			// TODO Auto-generated method stub
			return null;
		}
		public String getUsername() {
			// TODO Auto-generated method stub
			return null;
		}
		public CharSequence getPassword() {
			// TODO Auto-generated method stub
			return null;
		}
    }
}
