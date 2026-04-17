package com.example.studentmanagement.controller;

import com.example.studentmanagement.exception.GlobalExceptionHandler.ErrorResponse;
import com.example.studentmanagement.exception.GlobalExceptionHandler.ValidationErrorResponse;
import com.example.studentmanagement.model.Student;
import com.example.studentmanagement.service.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ PART II – Task 4 & 6: Student REST Controller with Full Swagger/OpenAPI Documentation
 *
 * Endpoints:
 *   POST   /students       → Create student
 *   GET    /students       → Get all students
 *   GET    /students/{id}  → Get student by ID
 *   PUT    /students/{id}  → Update student
 *   DELETE /students/{id}  → Delete student
 */
@RestController
@RequestMapping("/students")
@Tag(
    name = "Student Management",
    description = "REST APIs for managing Student records – Create, Read, Update, Delete (CRUD)"
)
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ─────────────────────────────────────────────────────────────
    // ✅ POST /students → Add a new student
    // ─────────────────────────────────────────────────────────────
    @Operation(
        summary     = "Add a new student",
        description = "Creates a new student record in the system. " +
                      "All fields (name, email, course) are required. " +
                      "Email must be unique across all students."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description  = "Student created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = Student.class),
                examples  = @ExampleObject(
                    name  = "Success Response",
                    value = """
                            {
                              "id": 1,
                              "name": "Ravi Kumar",
                              "email": "ravi.kumar@example.com",
                              "course": "Full Stack Application Development"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description  = "Validation failed – missing or invalid fields",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ValidationErrorResponse.class),
                examples  = @ExampleObject(
                    name  = "Validation Error",
                    value = """
                            {
                              "status": 400,
                              "error": "Validation Failed",
                              "fieldErrors": {
                                "email": "Email must be a valid email address",
                                "name": "Name must not be blank"
                              },
                              "timestamp": "2024-06-01T10:30:00"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description  = "Email already registered",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ErrorResponse.class),
                examples  = @ExampleObject(
                    value = """
                            {
                              "status": 409,
                              "error": "Conflict",
                              "message": "Email already registered: ravi.kumar@example.com",
                              "timestamp": "2024-06-01T10:30:00"
                            }
                            """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<Student> createStudent(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Student object to be created. The 'id' field is auto-generated.",
                required    = true,
                content     = @Content(
                    schema   = @Schema(implementation = Student.class),
                    examples = @ExampleObject(
                        name  = "Example Student",
                        value = """
                                {
                                  "name": "Ravi Kumar",
                                  "email": "ravi.kumar@example.com",
                                  "course": "Full Stack Application Development"
                                }
                                """
                    )
                )
            )
            @Valid @RequestBody Student student) {

        Student created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ─────────────────────────────────────────────────────────────
    // ✅ GET /students → Retrieve all students
    // ─────────────────────────────────────────────────────────────
    @Operation(
        summary     = "Get all students",
        description = "Retrieves a list of all student records stored in the system. " +
                      "Returns an empty array if no students exist."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description  = "List of students retrieved successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                array     = @ArraySchema(schema = @Schema(implementation = Student.class)),
                examples  = @ExampleObject(
                    name  = "Success Response",
                    value = """
                            [
                              {
                                "id": 1,
                                "name": "Ravi Kumar",
                                "email": "ravi.kumar@example.com",
                                "course": "Full Stack Application Development"
                              },
                              {
                                "id": 2,
                                "name": "Priya Singh",
                                "email": "priya.singh@example.com",
                                "course": "Data Science"
                              }
                            ]
                            """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ─────────────────────────────────────────────────────────────
    // ✅ GET /students/{id} → Retrieve student by ID
    // ─────────────────────────────────────────────────────────────
    @Operation(
        summary     = "Get student by ID",
        description = "Retrieves a single student record by their unique ID. " +
                      "Returns 404 if no student is found with the given ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description  = "Student found and returned",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = Student.class),
                examples  = @ExampleObject(
                    value = """
                            {
                              "id": 1,
                              "name": "Ravi Kumar",
                              "email": "ravi.kumar@example.com",
                              "course": "Full Stack Application Development"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "Student not found with given ID",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ErrorResponse.class),
                examples  = @ExampleObject(
                    name  = "Not Found – ID 999",
                    value = """
                            {
                              "status": 404,
                              "error": "Not Found",
                              "message": "Student not found with ID: 999",
                              "timestamp": "2024-06-01T10:30:00"
                            }
                            """
                )
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(
            @Parameter(
                description = "Unique ID of the student to retrieve",
                example     = "1",
                required    = true
            )
            @PathVariable Long id) {

        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // ─────────────────────────────────────────────────────────────
    // ✅ PUT /students/{id} → Update a student
    // ─────────────────────────────────────────────────────────────
    @Operation(
        summary     = "Update student by ID",
        description = "Updates the details of an existing student identified by their ID. " +
                      "All fields (name, email, course) must be provided. " +
                      "Returns 404 if the student doesn't exist, 409 if the new email is taken."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description  = "Student updated successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = Student.class),
                examples  = @ExampleObject(
                    value = """
                            {
                              "id": 1,
                              "name": "Ravi Kumar Updated",
                              "email": "ravi.new@example.com",
                              "course": "Cloud Computing"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description  = "Validation failed on request body",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ValidationErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "Student not found with given ID",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ErrorResponse.class),
                examples  = @ExampleObject(
                    value = """
                            {
                              "status": 404,
                              "error": "Not Found",
                              "message": "Student not found with ID: 999",
                              "timestamp": "2024-06-01T10:30:00"
                            }
                            """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409",
            description  = "Email already in use by another student",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @Parameter(
                description = "Unique ID of the student to update",
                example     = "1",
                required    = true
            )
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Updated student details",
                required    = true,
                content     = @Content(
                    schema   = @Schema(implementation = Student.class),
                    examples = @ExampleObject(
                        value = """
                                {
                                  "name": "Ravi Kumar Updated",
                                  "email": "ravi.new@example.com",
                                  "course": "Cloud Computing"
                                }
                                """
                    )
                )
            )
            @Valid @RequestBody Student studentDetails) {

        return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
    }

    // ─────────────────────────────────────────────────────────────
    // ✅ DELETE /students/{id} → Delete a student
    // ─────────────────────────────────────────────────────────────
    @Operation(
        summary     = "Delete student by ID",
        description = "Permanently deletes a student record from the system. " +
                      "Returns 204 No Content on success, 404 if ID doesn't exist."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description  = "Student deleted successfully – no content returned"
        ),
        @ApiResponse(
            responseCode = "404",
            description  = "Student not found with given ID",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema    = @Schema(implementation = ErrorResponse.class),
                examples  = @ExampleObject(
                    value = """
                            {
                              "status": 404,
                              "error": "Not Found",
                              "message": "Student not found with ID: 999",
                              "timestamp": "2024-06-01T10:30:00"
                            }
                            """
                )
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(
                description = "Unique ID of the student to delete",
                example     = "1",
                required    = true
            )
            @PathVariable Long id) {

        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
