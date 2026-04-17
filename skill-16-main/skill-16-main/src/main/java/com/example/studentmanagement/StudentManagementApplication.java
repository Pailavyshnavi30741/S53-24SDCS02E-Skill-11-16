package com.example.studentmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Student Management Spring Boot Application.
 * Swagger UI: http://localhost:8080/swagger-ui.html
 * API Docs:   http://localhost:8080/api-docs
 * H2 Console: http://localhost:8080/h2-console
 */
@SpringBootApplication
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
    }
}
