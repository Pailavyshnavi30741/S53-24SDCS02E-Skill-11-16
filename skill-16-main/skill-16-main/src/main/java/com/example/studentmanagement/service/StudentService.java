package com.example.studentmanagement.service;

import com.example.studentmanagement.model.Student;
import java.util.List;

/**
 * Service interface defining all student business operations.
 */
public interface StudentService {

    Student createStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(Long id);

    Student updateStudent(Long id, Student studentDetails);

    void deleteStudent(Long id);
}
