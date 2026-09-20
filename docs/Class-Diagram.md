# Class Diagram

```mermaid
classDiagram
    User "1" --> "*" Expense
    AuthController --> AuthService
    ExpenseController --> ExpenseService
    AuthService --> UserRepository
    ExpenseService --> ExpenseRepository
    AuthService --> JwtService
    JwtAuthenticationFilter --> JwtService

    class User {
      Long id
      String name
      String email
      String password
    }

    class Expense {
      Long id
      BigDecimal amount
      String category
      LocalDate expenseDate
      String description
    }

    class AuthService
    class ExpenseService
    class JwtService
    class AuthController
    class ExpenseController
}
```
