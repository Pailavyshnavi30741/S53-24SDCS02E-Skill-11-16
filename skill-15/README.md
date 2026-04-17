# Corporate Portal — JWT Authentication & Role-Based Authorization

A Spring Boot REST API demonstrating JWT-based authentication with RBAC (Role-Based Access Control).

---

## Tech Stack
| Technology        | Version  |
|-------------------|----------|
| Java              | 17       |
| Spring Boot       | 3.2.0    |
| Spring Security   | 6.x      |
| JJWT              | 0.11.5   |
| H2 (In-Memory DB) | Runtime  |
| Lombok            | Latest   |
| Maven             | Build    |

---

## Project Structure

```
src/main/java/com/corporate/portal/
│
├── CorporatePortalApplication.java      ← Main class + user seeder
│
├── entity/
│   └── User.java                        ← User entity (id, username, password, role)
│
├── repository/
│   └── UserRepository.java              ← JPA repository
│
├── util/
│   └── JwtUtil.java                     ← JWT generate, validate, extract
│
├── filter/
│   └── JwtAuthFilter.java               ← Intercepts every request, validates Bearer token
│
├── service/
│   └── CustomUserDetailsService.java    ← Loads user for Spring Security
│
├── config/
│   └── SecurityConfig.java              ← HttpSecurity, filter chain, roles
│
└── controller/
    ├── AuthController.java              ← POST /api/auth/login
    ├── AdminController.java             ← /api/admin/** (ADMIN only)
    └── EmployeeController.java          ← /api/employee/** (EMPLOYEE only)
```

---

## How to Run

```bash
# Clone the repository
git clone <your-repo-url>
cd corporate-portal

# Build and run
mvn spring-boot:run
```

Server starts on **http://localhost:8080**

---

## Default Users (Auto-Seeded)

| Username   | Password   | Role     |
|------------|------------|----------|
| `admin`    | `admin123` | ADMIN    |
| `employee` | `emp123`   | EMPLOYEE |

---

## API Endpoints

| Method | Endpoint                    | Access       | Description              |
|--------|-----------------------------|--------------|--------------------------|
| POST   | `/api/auth/login`           | Public       | Get JWT token            |
| POST   | `/api/admin/add`            | ADMIN only   | Add employee             |
| DELETE | `/api/admin/delete/{user}`  | ADMIN only   | Delete employee          |
| GET    | `/api/admin/users`          | ADMIN only   | List all users           |
| GET    | `/api/employee/profile`     | EMPLOYEE only| View own profile         |
| GET    | `/api/employee/dashboard`   | EMPLOYEE only| View dashboard           |

---

## Postman Testing Guide

### STEP 1 — Login as ADMIN

**Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Expected Response (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "admin",
  "role": "ADMIN",
  "message": "Login successful"
}
```
> Copy the `token` value for use in subsequent requests.

---

### STEP 2 — Add Employee (ADMIN Token)

**Request:**
```
POST http://localhost:8080/api/admin/add
Authorization: Bearer <ADMIN_TOKEN>
Content-Type: application/json

{
  "username": "john_doe",
  "password": "john123",
  "role": "EMPLOYEE"
}
```

**Expected Response (201 Created):**
```json
{
  "message": "Employee added successfully",
  "username": "john_doe",
  "role": "EMPLOYEE"
}
```

---

### STEP 3 — Delete Employee (ADMIN Token)

**Request:**
```
DELETE http://localhost:8080/api/admin/delete/john_doe
Authorization: Bearer <ADMIN_TOKEN>
```

**Expected Response (200 OK):**
```json
{
  "message": "Employee deleted successfully",
  "deletedUsername": "john_doe"
}
```

---

### STEP 4 — Login as EMPLOYEE

**Request:**
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "employee",
  "password": "emp123"
}
```
> Copy the employee token.

---

### STEP 5 — Access Employee Profile (EMPLOYEE Token)

**Request:**
```
GET http://localhost:8080/api/employee/profile
Authorization: Bearer <EMPLOYEE_TOKEN>
```

**Expected Response (200 OK):**
```json
{
  "id": 2,
  "username": "employee",
  "role": "EMPLOYEE",
  "message": "Welcome, employee! Here is your profile."
}
```

---

## Authorization Failure Test Cases

### Test A — No Token at All

```
GET http://localhost:8080/api/employee/profile
(No Authorization header)
```
**Expected: 403 Forbidden**

---

### Test B — Wrong Role (Employee accessing Admin endpoint)

```
POST http://localhost:8080/api/admin/add
Authorization: Bearer <EMPLOYEE_TOKEN>
Content-Type: application/json

{ "username": "test", "password": "test123", "role": "EMPLOYEE" }
```
**Expected: 403 Forbidden**

---

### Test C — Admin accessing Employee endpoint

```
GET http://localhost:8080/api/employee/profile
Authorization: Bearer <ADMIN_TOKEN>
```
**Expected: 403 Forbidden**

---

### Test D — Invalid/Expired Token

```
GET http://localhost:8080/api/employee/profile
Authorization: Bearer invalidtoken123
```
**Expected: 403 Forbidden**

---

### Test E — Wrong Credentials on Login

```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{ "username": "admin", "password": "wrongpassword" }
```
**Expected: 401 Unauthorized**
```json
{
  "error": "Invalid username or password"
}
```

---

## H2 Database Console

Access the in-memory database at:
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:corporatedb
Username: sa
Password: (empty)
```

---

## JWT Flow Diagram

```
Client                       Server
  |                             |
  |-- POST /api/auth/login ---> |
  |   { username, password }    |
  |                             |-- Validate credentials
  |                             |-- Generate JWT token
  |<-- 200 OK + JWT Token ----- |
  |                             |
  |-- GET /api/employee/profile |
  |   Authorization: Bearer JWT |
  |                             |-- JwtAuthFilter intercepts
  |                             |-- Validates token signature
  |                             |-- Extracts username & role
  |                             |-- Sets SecurityContext
  |<-- 200 OK + Profile Data -- |
```

---

## GitHub Push Instructions

```bash
git init
git add .
git commit -m "feat: JWT authentication with role-based authorization"
git branch -M main
git remote add origin https://github.com/<your-username>/corporate-portal.git
git push -u origin main
```
