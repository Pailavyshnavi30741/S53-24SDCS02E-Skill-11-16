package com.example.studentmanagement.repository;

import com.example.studentmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * JPA Repository for Student entity.
 * Provides built-in CRUD operations:
 *   save(), findById(), findAll(), deleteById(), existsById()
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Check if a student with given email already exists.
     * Used for duplicate email validation.
     */
    Optional<Student> findByEmail(String email);

    /**
     * Check existence by email, excluding a specific ID (for updates).
     */
    boolean existsByEmailAndIdNot(String email, Long id);
}
