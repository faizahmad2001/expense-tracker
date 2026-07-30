# Centa — Expense Tracker

A full-stack expense tracking application with JWT-secured REST APIs, a
Spring Boot + MySQL backend, and a React + Material UI frontend.

## Features

- User registration and login with JWT authentication
- Create, update, delete and view expenses
- Expense fields: title, amount, category, date, description
- Categories: Food, Travel, Shopping, Bills, Medical, Education, Other
- Dashboard: total expenses, monthly expenses, category-wise pie chart, recent expenses
- Search expenses by title
- Filter expenses by category and date range
- Server-side pagination and sorting
- Client-side form validation + server-side bean validation
- Centralized global exception handling with consistent JSON error payloads
- Swagger / OpenAPI documentation
- Responsive React UI (mobile, tablet, desktop)

## Tech Stack

**Backend:** Java 21, Spring Boot 3, Spring Data JPA, Spring Security, JWT (jjwt), MySQL, Maven, Lombok, springdoc-openapi

**Frontend:** React 18, React Router 6, Axios, Material UI 5, Recharts

## Folder Structure

```
expense-tracker/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/expensetracker/
│       │   ├── ExpenseTrackerApplication.java
│       │   ├── config/          # SwaggerConfig
│       │   ├── controller/      # AuthController, ExpenseController, DashboardController
│       │   ├── dto/             # Request/response payloads
│       │   ├── entity/          # User, Expense, Category
│       │   ├── exception/       # GlobalExceptionHandler + custom exceptions
│       │   ├── repository/      # UserRepository, ExpenseRepository
│       │   ├── security/        # JWT filter/utils, SecurityConfig, UserDetails
│       │   └── service/         # AuthService, ExpenseService, DashboardService
│       └── resources/
│           └── application.yml
├── database/
│   ├── schema.sql               # DDL (optional — Hibernate can auto-create)
│   └── data.sql                 # Sample seed data
├── frontend/
│   ├── public/
│   └── src/
│       ├── api/                 # axios instance + API service functions
│       ├── components/          # Layout, ExpenseFormDialog, ConfirmDialog, PrivateRoute
│       ├── context/              # AuthContext
│       ├── pages/                # Login, Register, Dashboard, Expenses
│       ├── utils/                # constants/helpers
│       ├── App.js
│       ├── theme.js
│       └── index.js
└── README.md
```

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- Node.js 18+ and npm
- MySQL 8+

### 1. Database setup

```sql
CREATE DATABASE expense_tracker_db;
```

Hibernate (`ddl-auto: update`) will create/update the tables automatically on
first run. Alternatively, run the DDL manually:

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql   # optional sample data
```

Sample login (if you load `data.sql`): `username: johndoe`, `password: password123`.

### 2. Backend

```bash
cd backend
# set DB credentials via env vars (or edit application.yml directly)
export DB_USERNAME=root
export DB_PASSWORD=your_password
export JWT_SECRET=$(openssl rand -hex 32)

mvn spring-boot:run
```

The API starts on **http://localhost:8080**.
Swagger UI: **http://localhost:8080/swagger-ui.html**

### 3. Frontend

```bash
cd frontend
npm install
npm start
```

The app starts on **http://localhost:3000** and talks to the API via the
`REACT_APP_API_BASE_URL` value in `frontend/.env` (defaults to
`http://localhost:8080/api`).

## REST API Overview

| Method | Endpoint                | Description                              | Auth |
|--------|--------------------------|-------------------------------------------|------|
| POST   | `/api/auth/register`     | Register a new user, returns JWT          | No   |
| POST   | `/api/auth/login`        | Login, returns JWT                        | No   |
| GET    | `/api/expenses`          | Search/filter/sort/paginate expenses      | Yes  |
| POST   | `/api/expenses`          | Create an expense                         | Yes  |
| GET    | `/api/expenses/{id}`     | Get a single expense                      | Yes  |
| PUT    | `/api/expenses/{id}`     | Update an expense                         | Yes  |
| DELETE | `/api/expenses/{id}`     | Delete an expense                         | Yes  |
| GET    | `/api/dashboard/summary` | Dashboard totals + chart + recent expenses| Yes  |

`GET /api/expenses` query params: `title`, `category`, `startDate`, `endDate`,
`page`, `size`, `sortBy` (`date`\|`amount`\|`title`\|`category`), `direction` (`asc`\|`desc`).

Send the JWT on protected endpoints as: `Authorization: Bearer <token>`

## Security Notes

- Passwords are hashed with BCrypt.
- Stateless JWT auth (no server-side sessions); token is validated on every request via a custom filter.
- CORS is restricted to the origin configured in `app.cors.allowed-origins`.
- Every expense operation is scoped to the authenticated user — one user can never read/edit/delete another user's data.
- **Before deploying to production:** change `app.jwt.secret` to a strong, randomly generated value and never commit real secrets to source control.

## License

This project is provided as-is for learning and demonstration purposes.
