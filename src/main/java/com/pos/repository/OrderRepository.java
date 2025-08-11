package com.pos.repository;

import com.pos.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    List<Order> findByUser_Id(Long userId);
    
    List<Order> findByProduct_ProductId(Long productId);
    
    List<Order> findByDate(LocalDate date);
    
    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT o FROM Order o WHERE " +
           "LOWER(o.orderTag) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(o.product.productName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(o.customerFullName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(o.customerPhone) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(o.paymentMethod) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<Order> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(o) FROM Order o")
    Long countTotalOrders();
    
    @Query("SELECT COUNT(o) FROM Order o WHERE o.date = CURRENT_DATE")
    Long countTodayOrders();
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.date = CURRENT_DATE")
    Double sumTodaySales();
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.date >= :startDate AND o.date <= :endDate")
    Double sumSalesBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(o.totalAmount) FROM Order o")
    Double sumTotalSales();
}