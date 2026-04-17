package com.example.studentmanagement.service;

import com.example.studentmanagement.exception.StudentNotFoundException;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of StudentService with full CRUD business logic.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Create a new student.
     * Throws IllegalArgumentException if email is already registered.
     */
    @Override
    public Student createStudent(Student student) {
        studentRepository.findByEmail(student.getEmail()).ifPresent(existing -> {
            throw new IllegalArgumentException(
                "Email already registered: " + student.getEmail()
            );
        });
        return studentRepository.save(student);
    }

    /**
     * Retrieve all students from the database.
     */
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    /**
     * Find a student by ID.
     * Throws StudentNotFoundException if not found.
     */
    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    /**
     * Update an existing student's details.
     * Throws StudentNotFoundException if ID doesn't exist.
     * Throws IllegalArgumentException if new email conflicts with another student.
     */
    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // Validate email uniqueness (allow same email for same student)
        if (studentRepository.existsByEmailAndIdNot(studentDetails.getEmail(), id)) {
            throw new IllegalArgumentException(
                "Email already in use by another student: " + studentDetails.getEmail()
            );
        }

        existing.setName(studentDetails.getName());
        existing.setEmail(studentDetails.getEmail());
        existing.setCourse(studentDetails.getCourse());

        return studentRepository.save(existing);
    }

    /**
     * Delete a student by ID.
     * Throws StudentNotFoundException if not found.
     */
    @Override
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}
