package com.faiz.expensetracker.repository;

import com.faiz.expensetracker.entity.Expense;
import com.faiz.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByUserOrderByExpenseDateDesc(User user);
    Optional<Expense> findByIdAndUser(Long id, User user);
}
