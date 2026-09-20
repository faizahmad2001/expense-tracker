# API Reference

## Authentication

### Register
`POST /api/auth/register`

```json
{"name":"Faiz Ahmad","email":"faiz@example.com","password":"Password@123"}
```

### Login
`POST /api/auth/login`

```json
{"email":"faiz@example.com","password":"Password@123"}
```

The response contains a JWT token.

## Expenses

Send the token in:
`Authorization: Bearer <JWT>`

- `GET /api/expenses`
- `POST /api/expenses`
- `PUT /api/expenses/{id}`
- `DELETE /api/expenses/{id}`
- `GET /api/expenses/summary`

Create/update body:
```json
{
  "amount": 450.00,
  "category": "Food",
  "expenseDate": "2026-09-19",
  "description": "Lunch"
}
```
