package com.faiz.expensetracker.dto;

import java.math.BigDecimal;

public record SummaryResponse(long count, BigDecimal total) {}
