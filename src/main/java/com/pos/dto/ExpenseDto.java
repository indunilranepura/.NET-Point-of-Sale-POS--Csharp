package com.pos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDto {
    
    private Long expenseId;
    
    @NotBlank(message = "Expense name is required")
    @Size(max = 200)
    private String expenseName;
    
    @DecimalMin(value = "0.0", message = "Expense amount must be positive")
    @NotNull(message = "Expense amount is required")
    private Double expenseAmount;
    
    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;
}