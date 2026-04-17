package com.example.studentmanagement.controller;

import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;
import com.example.studentmanagement.exception.StudentNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for StudentController.
 * Tests all 5 CRUD endpoints.
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    // ─── Test: POST /students ────────────────────────────────────

    @Test
    void createStudent_ShouldReturn201() throws Exception {
        Student input  = new Student("Ravi Kumar", "ravi@example.com", "FSAD");
        Student saved  = new Student("Ravi Kumar", "ravi@example.com", "FSAD");
        saved.setId(1L);

        when(studentService.createStudent(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ravi Kumar"))
                .andExpect(jsonPath("$.email").value("ravi@example.com"));
    }

    @Test
    void createStudent_WithInvalidData_ShouldReturn400() throws Exception {
        Student invalid = new Student("", "not-an-email", "");

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    // ─── Test: GET /students ─────────────────────────────────────

    @Test
    void getAllStudents_ShouldReturn200WithList() throws Exception {
        List<Student> students = Arrays.asList(
                new Student("Ravi", "ravi@example.com", "FSAD"),
                new Student("Priya", "priya@example.com", "DS")
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ─── Test: GET /students/{id} ────────────────────────────────

    @Test
    void getStudentById_ShouldReturn200() throws Exception {
        Student student = new Student("Ravi Kumar", "ravi@example.com", "FSAD");
        student.setId(1L);

        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Ravi Kumar"));
    }

    @Test
    void getStudentById_WithInvalidId_ShouldReturn404() throws Exception {
        when(studentService.getStudentById(999L))
                .thenThrow(new StudentNotFoundException(999L));

        mockMvc.perform(get("/students/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student not found with ID: 999"));
    }

    // ─── Test: PUT /students/{id} ────────────────────────────────

    @Test
    void updateStudent_ShouldReturn200() throws Exception {
        Student updated = new Student("Updated Name", "updated@example.com", "AI");
        updated.setId(1L);

        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"));
    }

    // ─── Test: DELETE /students/{id} ─────────────────────────────

    @Test
    void deleteStudent_ShouldReturn204() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_WithInvalidId_ShouldReturn404() throws Exception {
        doThrow(new StudentNotFoundException(999L))
                .when(studentService).deleteStudent(999L);

        mockMvc.perform(delete("/students/999"))
                .andExpect(status().isNotFound());
    }
}
