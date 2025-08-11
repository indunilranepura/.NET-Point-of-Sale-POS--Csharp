package com.pos.controller;

import com.pos.dto.OrderDto;
import com.pos.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class OrderController {
    
    private final OrderService orderService;
    
    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam(required = false) String search) {
        List<OrderDto> orders = orderService.searchOrders(search);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        OrderDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER') or hasRole('SALESMAN')")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        OrderDto createdOrder = orderService.createOrder(orderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('CASHIER')")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable Long id, 
                                              @Valid @RequestBody OrderDto orderDto) {
        OrderDto updatedOrder = orderService.updateOrder(id, orderDto);
        return ResponseEntity.ok(updatedOrder);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalOrderCount() {
        Long count = orderService.getTotalOrderCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTodayOrderCount() {
        Long count = orderService.getTodayOrderCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/sales/today")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTodaySales() {
        Double sales = orderService.getTodaySales();
        return ResponseEntity.ok(sales);
    }
    
    @GetMapping("/stats/sales/weekly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getWeeklySales() {
        Double sales = orderService.getWeeklySales();
        return ResponseEntity.ok(sales);
    }
    
    @GetMapping("/stats/sales/monthly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getMonthlySales() {
        Double sales = orderService.getMonthlySales();
        return ResponseEntity.ok(sales);
    }
    
    @GetMapping("/stats/sales/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Double> getTotalSales() {
        Double sales = orderService.getTotalSales();
        return ResponseEntity.ok(sales);
    }
}