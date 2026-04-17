package com.example.studentmanagement.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * ✅ PART II – Task 5: Student Entity
 *
 * Represents a Student record in the database.
 * Fields: id, name, email, course
 *
 * OpenAPI @Schema annotations generate automatic model/schema
 * documentation visible in Swagger UI under "Schemas" section.
 */
@Entity
@Table(name = "students")
@Schema(
    name = "Student",
    description = "Student entity representing a student record in the system"
)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Auto-generated unique identifier for the student",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(
        description = "Full name of the student",
        example = "Ravi Kumar",
        requiredMode = Schema.RequiredMode.REQUIRED,
        minLength = 2,
        maxLength = 100
    )
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Schema(
        description = "Unique email address of the student",
        example = "ravi.kumar@example.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Course must not be blank")
    @Schema(
        description = "Course or program the student is enrolled in",
        example = "Full Stack Application Development",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Column(nullable = false)
    private String course;

    // ─── Constructors ────────────────────────────────────────────
    public Student() {}

    public Student(String name, String email, String course) {
        this.name   = name;
        this.email  = email;
        this.course = course;
    }

    // ─── Getters & Setters ────────────────────────────────────────
    public Long getId()             { return id; }
    public void setId(Long id)      { this.id = id; }

    public String getName()             { return name; }
    public void setName(String name)    { this.name = name; }

    public String getEmail()              { return email; }
    public void setEmail(String email)    { this.email = email; }

    public String getCourse()               { return course; }
    public void setCourse(String course)    { this.course = course; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email + "', course='" + course + "'}";
    }
}
