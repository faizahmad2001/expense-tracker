package com.faiz.expensetracker.service;

import com.faiz.expensetracker.dto.*;
import com.faiz.expensetracker.entity.*;
import com.faiz.expensetracker.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ExpenseService {
    private final ExpenseRepository expenses;
    private final UserRepository users;

    public ExpenseService(ExpenseRepository expenses, UserRepository users) {
        this.expenses = expenses;
        this.users = users;
    }

    private User currentUser(String email) {
        return users.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public List<ExpenseResponse> findAll(String email) {
        return expenses.findAllByUserOrderByExpenseDateDesc(currentUser(email))
                .stream().map(this::toResponse).toList();
    }

    public ExpenseResponse create(String email, ExpenseRequest request) {
        User user = currentUser(email);
        Expense expense = Expense.builder()
                .amount(request.amount())
                .category(request.category().trim())
                .expenseDate(request.expenseDate())
                .description(request.description())
                .user(user)
                .build();
        return toResponse(expenses.save(expense));
    }

    public ExpenseResponse update(String email, Long id, ExpenseRequest request) {
        User user = currentUser(email);
        Expense expense = expenses.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        expense.setAmount(request.amount());
        expense.setCategory(request.category().trim());
        expense.setExpenseDate(request.expenseDate());
        expense.setDescription(request.description());
        return toResponse(expenses.save(expense));
    }

    public void delete(String email, Long id) {
        User user = currentUser(email);
        Expense expense = expenses.findByIdAndUser(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Expense not found"));
        expenses.delete(expense);
    }

    public SummaryResponse summary(String email) {
        List<Expense> list = expenses.findAllByUserOrderByExpenseDateDesc(currentUser(email));
        BigDecimal total = list.stream().map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new SummaryResponse(list.size(), total);
    }

    private ExpenseResponse toResponse(Expense e) {
        return new ExpenseResponse(e.getId(), e.getAmount(), e.getCategory(),
                e.getExpenseDate(), e.getDescription());
    }
}
