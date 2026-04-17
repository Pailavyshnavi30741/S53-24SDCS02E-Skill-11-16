package com.example.studentmanagement.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ✅ PART II – Task 6c: Global Exception Handler
 *
 * Handles all application exceptions and returns clean, structured
 * error responses that Swagger UI can display properly.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─── Error Response DTO ──────────────────────────────────────
    @Schema(name = "ErrorResponse", description = "Standard error response structure")
    public static class ErrorResponse {

        @Schema(description = "HTTP status code", example = "404")
        private int status;

        @Schema(description = "Error type", example = "Not Found")
        private String error;

        @Schema(description = "Detailed error message", example = "Student not found with ID: 999")
        private String message;

        @Schema(description = "Timestamp of the error", example = "2024-06-01T10:30:00")
        private LocalDateTime timestamp;

        public ErrorResponse(int status, String error, String message) {
            this.status    = status;
            this.error     = error;
            this.message   = message;
            this.timestamp = LocalDateTime.now();
        }

        public int getStatus()             { return status; }
        public String getError()           { return error; }
        public String getMessage()         { return message; }
        public LocalDateTime getTimestamp(){ return timestamp; }
    }

    // ─── Validation Error Response DTO ───────────────────────────
    @Schema(name = "ValidationErrorResponse", description = "Validation error response with field-level details")
    public static class ValidationErrorResponse {

        @Schema(description = "HTTP status code", example = "400")
        private int status;

        @Schema(description = "Error type", example = "Validation Failed")
        private String error;

        @Schema(description = "Map of field names to validation messages")
        private Map<String, String> fieldErrors;

        @Schema(description = "Timestamp of the error")
        private LocalDateTime timestamp;

        public ValidationErrorResponse(int status, String error, Map<String, String> fieldErrors) {
            this.status      = status;
            this.error       = error;
            this.fieldErrors = fieldErrors;
            this.timestamp   = LocalDateTime.now();
        }

        public int getStatus()                    { return status; }
        public String getError()                  { return error; }
        public Map<String, String> getFieldErrors(){ return fieldErrors; }
        public LocalDateTime getTimestamp()       { return timestamp; }
    }

    /**
     * Handle StudentNotFoundException → 404 Not Found
     */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFound(StudentNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * Handle Bean Validation errors → 400 Bad Request
     * Triggered when @Valid fails on request body.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                fieldErrors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handle duplicate email conflict → 409 Conflict
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleConflict(IllegalArgumentException ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * Handle all other unexpected exceptions → 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
