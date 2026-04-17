package com.example.studentmanagement.config;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Loads sample student data on application startup.
 * Useful for testing Swagger UI without manually adding students first.
 */
@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadSampleData(StudentRepository repository) {
        return args -> {
            repository.save(new Student("Ravi Kumar",   "ravi.kumar@example.com",   "Full Stack Application Development"));
            repository.save(new Student("Priya Singh",  "priya.singh@example.com",  "Data Science"));
            repository.save(new Student("Arjun Mehta",  "arjun.mehta@example.com",  "Artificial Intelligence"));
            repository.save(new Student("Sneha Reddy",  "sneha.reddy@example.com",   "Cybersecurity"));
            repository.save(new Student("Kiran Sharma", "kiran.sharma@example.com", "Cloud Computing"));

            System.out.println("=================================================");
            System.out.println("✅ Sample student data loaded successfully!");
            System.out.println("=================================================");
            System.out.println("🌐 Swagger UI  → http://localhost:8080/swagger-ui.html");
            System.out.println("📄 API Docs    → http://localhost:8080/api-docs");
            System.out.println("🗄️  H2 Console  → http://localhost:8080/h2-console");
            System.out.println("=================================================");
        };
    }
}
