package com.corporate.portal;

import com.corporate.portal.entity.User;
import com.corporate.portal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CorporatePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(CorporatePortalApplication.class, args);
    }

    // Seed default users on startup
    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("employee").isEmpty()) {
                User employee = new User();
                employee.setUsername("employee");
                employee.setPassword(passwordEncoder.encode("emp123"));
                employee.setRole("EMPLOYEE");
                userRepository.save(employee);
            }

            System.out.println("Users checked/created successfully");
        };
    }
}
