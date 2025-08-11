package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Expense extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;
    
    @NotBlank(message = "Expense name is required")
    @Size(max = 200)
    @Column(name = "expense_name", nullable = false)
    private String expenseName;
    
    @DecimalMin(value = "0.0", message = "Expense amount must be positive")
    @Column(name = "expense_amount", nullable = false)
    private Double expenseAmount;
    
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;
}