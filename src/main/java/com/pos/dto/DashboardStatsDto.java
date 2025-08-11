package com.pos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    
    // User statistics
    private Long totalUsers;
    private Long totalAdmins;
    private Long totalCashiers;
    private Long totalSalesmen;
    
    // Salary statistics
    private Double totalSalary;
    private Double adminSalary;
    private Double cashierSalary;
    private Double salesmanSalary;
    
    // Product statistics
    private Long totalProducts;
    private Long availableProducts;
    private Long unavailableProducts;
    
    // Order statistics
    private Long totalOrders;
    private Long todayOrders;
    private Double todaySales;
    private Double weeklySales;
    private Double monthlySales;
    private Double totalSales;
    
    // Expense statistics
    private Double todayExpenses;
    private Double weeklyExpenses;
    private Double monthlyExpenses;
    private Double totalExpenses;
    
    // Category statistics
    private Long totalMainCategories;
    private Long totalSecondCategories;
    private Long totalThirdCategories;
    private Long totalVendors;
    private Long totalBrands;
}