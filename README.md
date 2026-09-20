# Centa — Expense Tracker

A production-style full-stack Expense Tracker built with **Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA, MySQL and React**.

## Features
- User registration and JWT-based login
- Password hashing with BCrypt
- Create, read, update and delete expenses
- Expense categories
- Date and description fields
- Per-user data isolation
- REST API
- React dashboard
- Expense totals and category summary
- Form validation and API error handling
- Unit tests for service layer
- ER diagram and class diagram

## Technology Stack
**Backend:** Java 21, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate, MySQL, Maven  
**Frontend:** React, Vite, JavaScript, CSS  
**Testing:** JUnit 5, Mockito, Spring Boot Test

## Project Structure
```text
expense-tracker/
├── backend/
│   ├── pom.xml
│   └── src/
│       ├── main/java/com/faiz/expensetracker/
│       │   ├── config/
│       │   ├── controller/
│       │   ├── dto/
│       │   ├── entity/
│       │   ├── exception/
│       │   ├── repository/
│       │   ├── security/
│       │   └── service/
│       └── test/
├── frontend/
│   ├── package.json
│   ├── index.html
│   └── src/
├── docs/
│   ├── ER-Diagram.md
│   └── Class-Diagram.md
└── README.md
```

## Run Backend

1. Create a MySQL database:
```sql
CREATE DATABASE expense_tracker;
```

2. Update `backend/src/main/resources/application.properties` with your MySQL username/password.

3. Run:
```bash
cd backend
mvn spring-boot:run
```

Backend runs on `http://localhost:8080`.

## Run Frontend
```bash
cd frontend
npm install
npm run dev
```

Frontend runs on `http://localhost:5173`.

## Default API
- `POST /api/auth/register`
- `POST /api/auth/login`
- `GET /api/expenses`
- `POST /api/expenses`
- `PUT /api/expenses/{id}`
- `DELETE /api/expenses/{id}`
- `GET /api/expenses/summary`

All expense endpoints require `Authorization: Bearer <token>`.

## Example Registration
```json
{
  "name": "Faiz Ahmad",
  "email": "faiz@example.com",
  "password": "Password@123"
}
```

## Security
Passwords are never stored as plain text. JWT is used for stateless authentication and each user can access only their own expenses.

## GitHub
This repository is structured as a portfolio-ready full-stack project. Before publishing, replace any local credentials in `application.properties` with environment variables or a local ignored configuration.
