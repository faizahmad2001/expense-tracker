package com.faiz.expensetracker.service;

import com.faiz.expensetracker.dto.*;
import com.faiz.expensetracker.entity.*;
import com.faiz.expensetracker.repository.*;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ExpenseServiceTest {
    @Mock ExpenseRepository expenseRepository;
    @Mock UserRepository userRepository;
    @InjectMocks ExpenseService expenseService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void summaryShouldCalculateTotal() {
        User user = User.builder().id(1L).email("faiz@example.com").build();
        Expense a = Expense.builder().amount(new BigDecimal("100.00")).user(user)
                .category("Food").expenseDate(LocalDate.now()).build();
        Expense b = Expense.builder().amount(new BigDecimal("250.50")).user(user)
                .category("Travel").expenseDate(LocalDate.now()).build();

        when(userRepository.findByEmailIgnoreCase("faiz@example.com")).thenReturn(java.util.Optional.of(user));
        when(expenseRepository.findAllByUserOrderByExpenseDateDesc(user)).thenReturn(List.of(a, b));

        SummaryResponse result = expenseService.summary("faiz@example.com");

        assertThat(result.count()).isEqualTo(2);
        assertThat(result.total()).isEqualByComparingTo("350.50");
    }
}
