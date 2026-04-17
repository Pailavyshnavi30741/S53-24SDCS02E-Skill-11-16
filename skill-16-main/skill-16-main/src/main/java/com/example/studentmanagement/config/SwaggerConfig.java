package com.example.studentmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * ✅ PART I – Task 2: SwaggerConfig (OpenAPI Configuration)
 *
 * This class configures the OpenAPI/Swagger documentation for the
 * Student Management REST API.
 *
 * Access Swagger UI at:
 *   → http://localhost:8080/swagger-ui.html
 *   → http://localhost:8080/swagger-ui/index.html
 *
 * Access raw JSON API docs at:
 *   → http://localhost:8080/api-docs
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Management REST API")
                        .description(
                                "Full-Stack Student CRUD Application – Spring Boot REST API\n\n" +
                                "**SKILL-16 | FSAD Workbook**\n\n" +
                                "This API provides endpoints to Create, Read, Update, and Delete student records.\n\n" +
                                "**Available Endpoints:**\n" +
                                "- `POST   /students`       → Add a new student\n" +
                                "- `GET    /students`       → Get all students\n" +
                                "- `GET    /students/{id}`  → Get student by ID\n" +
                                "- `PUT    /students/{id}`  → Update student by ID\n" +
                                "- `DELETE /students/{id}`  → Delete student by ID\n\n" +
                                "**H2 Console:** http://localhost:8080/h2-console"
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("FSAD Team")
                                .email("fsad@example.com")
                                .url("https://example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Development Server")
                ));
    }
}
