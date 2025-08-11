package com.pos.repository;

import com.pos.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    List<Expense> findByExpenseDate(LocalDate expenseDate);
    
    List<Expense> findByExpenseDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT e FROM Expense e WHERE " +
           "LOWER(e.expenseName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "CAST(e.expenseAmount AS string) LIKE CONCAT('%', :searchKey, '%')")
    List<Expense> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e WHERE e.expenseDate = CURRENT_DATE")
    Double sumTodayExpenses();
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e WHERE e.expenseDate >= :startDate AND e.expenseDate <= :endDate")
    Double sumExpensesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(e.expenseAmount) FROM Expense e")
    Double sumTotalExpenses();
}