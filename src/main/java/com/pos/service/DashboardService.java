package com.pos.service;

import com.pos.dto.DashboardStatsDto;
import com.pos.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final UserService userService;
    private final ProductService productService;
    private final OrderService orderService;
    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    
    public DashboardStatsDto getDashboardStats() {
        DashboardStatsDto stats = new DashboardStatsDto();
        
        // User statistics
        stats.setTotalUsers(userService.getTotalUserCount());
        stats.setTotalAdmins(userService.getUserCountByRole(User.Role.ADMIN));
        stats.setTotalCashiers(userService.getUserCountByRole(User.Role.CASHIER));
        stats.setTotalSalesmen(userService.getUserCountByRole(User.Role.SALESMAN));
        
        // Salary statistics
        stats.setTotalSalary(userService.getTotalSalary());
        stats.setAdminSalary(userService.getSalaryByRole(User.Role.ADMIN));
        stats.setCashierSalary(userService.getSalaryByRole(User.Role.CASHIER));
        stats.setSalesmanSalary(userService.getSalaryByRole(User.Role.SALESMAN));
        
        // Product statistics
        stats.setTotalProducts(productService.getTotalProductCount());
        stats.setAvailableProducts(productService.getAvailableProductCount());
        stats.setUnavailableProducts(productService.getUnavailableProductCount());
        
        // Order statistics
        stats.setTotalOrders(orderService.getTotalOrderCount());
        stats.setTodayOrders(orderService.getTodayOrderCount());
        stats.setTodaySales(orderService.getTodaySales());
        stats.setWeeklySales(orderService.getWeeklySales());
        stats.setMonthlySales(orderService.getMonthlySales());
        stats.setTotalSales(orderService.getTotalSales());
        
        // Expense statistics
        stats.setTodayExpenses(expenseService.getTodayExpenses());
        stats.setWeeklyExpenses(expenseService.getWeeklyExpenses());
        stats.setMonthlyExpenses(expenseService.getMonthlyExpenses());
        stats.setTotalExpenses(expenseService.getTotalExpenses());
        
        // Category statistics
        stats.setTotalMainCategories(categoryService.getMainCategoryCount());
        stats.setTotalSecondCategories(categoryService.getSecondCategoryCount());
        stats.setTotalThirdCategories(categoryService.getThirdCategoryCount());
        
        return stats;
    }
}