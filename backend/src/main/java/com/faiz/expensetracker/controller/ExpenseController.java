package com.faiz.expensetracker.controller;

import com.faiz.expensetracker.dto.*;
import com.faiz.expensetracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public List<ExpenseResponse> all(Authentication auth) {
        return expenseService.findAll(auth.getName());
    }

    @PostMapping
    public ResponseEntity<ExpenseResponse> create(Authentication auth,
                                                    @Valid @RequestBody ExpenseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(expenseService.create(auth.getName(), request));
    }

    @PutMapping("/{id}")
    public ExpenseResponse update(Authentication auth, @PathVariable Long id,
                                  @Valid @RequestBody ExpenseRequest request) {
        return expenseService.update(auth.getName(), id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(Authentication auth, @PathVariable Long id) {
        expenseService.delete(auth.getName(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public SummaryResponse summary(Authentication auth) {
        return expenseService.summary(auth.getName());
    }
}
