# FlowDesk – Personal Productivity Dashboard

## Tech Stack
- Java 26 + Spring Boot 3.4.5
- Spring Web, Spring Data JPA, Spring Security
- SQLite (file-based, zero setup)
- JWT Authentication
- Lombok

---

## How to Run in IntelliJ

1. Open IntelliJ → File → Open → select the `flowdesk` folder
2. Wait for Maven to download all dependencies (bottom progress bar)
3. Open `FlowDeskApplication.java`
4. Click the green ▶ Run button
5. Server starts at: http://localhost:8080
6. SQLite database file `flowdesk.db` is auto-created in the project root

---

## Test Auth APIs (use Postman or curl)

### Register
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "name": "Aadil",
  "email": "aadil@flowdesk.com",
  "password": "secret123"
}

### Login
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "aadil@flowdesk.com",
  "password": "secret123"
}

Response gives you a JWT token. Use it as:
Authorization: Bearer <token>

---

## Project Structure

src/main/java/com/flowdesk/

├── FlowDeskApplication.java

├── config/

│   ├── JwtUtil.java

│   ├── JwtAuthFilter.java

│   └── SecurityConfig.java

├── controller/

│   └── AuthController.java      

├── service/

│   └── AuthService.java      

├── repository/

│   ├── UserRepository.java

│   ├── TaskRepository.java

│   ├── ScheduleRepository.java

│   ├── HabitRepository.java

│   └── NoteRepository.java

├── model/

│   ├── User.java

│   ├── Task.java

│   ├── Schedule.java

│   ├── Habit.java

│   └── Note.java

└── dto/
    ├── RegisterRequest.java
    ├── LoginRequest.java
    └── AuthResponse.java

---

