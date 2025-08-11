package com.pos.service;

import com.pos.dto.ExpenseDto;
import com.pos.entity.Expense;
import com.pos.exception.ResourceNotFoundException;
import com.pos.mapper.ExpenseMapper;
import com.pos.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    
    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<ExpenseDto> searchExpenses(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllExpenses();
        }
        return expenseRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(expenseMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public ExpenseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        return expenseMapper.toDto(expense);
    }
    
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        Expense expense = expenseMapper.toEntity(expenseDto);
        
        // Set date if not provided
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDate.now());
        }
        
        Expense savedExpense = expenseRepository.save(expense);
        log.info("Created new expense with ID: {}", savedExpense.getExpenseId());
        
        return expenseMapper.toDto(savedExpense);
    }
    
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        
        // Update fields
        existingExpense.setExpenseName(expenseDto.getExpenseName());
        existingExpense.setExpenseAmount(expenseDto.getExpenseAmount());
        existingExpense.setExpenseDate(expenseDto.getExpenseDate());
        
        Expense savedExpense = expenseRepository.save(existingExpense);
        log.info("Updated expense with ID: {}", savedExpense.getExpenseId());
        
        return expenseMapper.toDto(savedExpense);
    }
    
    public void deleteExpense(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
        
        expenseRepository.delete(expense);
        log.info("Deleted expense with ID: {}", expense.getExpenseId());
    }
    
    // Dashboard statistics methods
    public Double getTodayExpenses() {
        return expenseRepository.sumTodayExpenses();
    }
    
    public Double getWeeklyExpenses() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        return expenseRepository.sumExpensesBetweenDates(startDate, endDate);
    }
    
    public Double getMonthlyExpenses() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        return expenseRepository.sumExpensesBetweenDates(startDate, endDate);
    }
    
    public Double getTotalExpenses() {
        return expenseRepository.sumTotalExpenses();
    }
}