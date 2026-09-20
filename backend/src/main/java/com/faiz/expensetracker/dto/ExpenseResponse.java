package com.faiz.expensetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
        Long id, BigDecimal amount, String category,
        LocalDate expenseDate, String description
) {}
