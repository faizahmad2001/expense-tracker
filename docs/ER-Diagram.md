# ER Diagram

```mermaid
erDiagram
    USERS ||--o{ EXPENSES : owns

    USERS {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR password
    }

    EXPENSES {
        BIGINT id PK
        DECIMAL amount
        VARCHAR category
        DATE expense_date
        VARCHAR description
        BIGINT user_id FK
    }
```
