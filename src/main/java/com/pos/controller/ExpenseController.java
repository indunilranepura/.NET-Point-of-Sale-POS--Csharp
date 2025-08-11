package com.pos.controller;

import com.pos.dto.ExpenseDto;
import com.pos.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ExpenseController {
    
    private final ExpenseService expenseService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@RequestParam(required = false) String search) {
        List<ExpenseDto> expenses = expenseService.searchExpenses(search);
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        ExpenseDto expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpenseDto> createExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto createdExpense = expenseService.createExpense(expenseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, 
                                                  @Valid @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTodayExpenses() {
        Double expenses = expenseService.getTodayExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/stats/weekly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getWeeklyExpenses() {
        Double expenses = expenseService.getWeeklyExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/stats/monthly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getMonthlyExpenses() {
        Double expenses = expenseService.getMonthlyExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTotalExpenses() {
        Double expenses = expenseService.getTotalExpenses();
        return ResponseEntity.ok(expenses);
    }
}