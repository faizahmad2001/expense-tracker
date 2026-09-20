package com.faiz.expensetracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
        @NotNull @DecimalMin("0.01") BigDecimal amount,
        @NotBlank @Size(max = 60) String category,
        @NotNull LocalDate expenseDate,
        @Size(max = 255) String description
) {}
