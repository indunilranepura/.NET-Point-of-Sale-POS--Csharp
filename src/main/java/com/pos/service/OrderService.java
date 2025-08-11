package com.pos.service;

import com.pos.dto.OrderDto;
import com.pos.entity.*;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.InsufficientStockException;
import com.pos.mapper.OrderMapper;
import com.pos.repository.*;
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
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BarCodeRepository barCodeRepository;
    private final OrderMapper orderMapper;
    
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<OrderDto> searchOrders(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllOrders();
        }
        return orderRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(orderMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toDto(order);
    }
    
    public OrderDto createOrder(OrderDto orderDto) {
        // Validate user exists
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + orderDto.getUserId()));
        
        // Validate product exists
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + orderDto.getProductId()));
        
        // Check stock availability
        if (product.getProductUnitStock() < orderDto.getOrderQuantity()) {
            throw new InsufficientStockException("Insufficient stock. Available: " + product.getProductUnitStock() + 
                    ", Requested: " + orderDto.getOrderQuantity());
        }
        
        // Validate barcode exists
        BarCode barCode = barCodeRepository.findById(orderDto.getBarCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("BarCode not found with id: " + orderDto.getBarCodeId()));
        
        Order order = orderMapper.toEntity(orderDto);
        order.setUser(user);
        order.setProduct(product);
        order.setBarCode(barCode);
        
        // Generate order tag if not provided
        if (order.getOrderTag() == null || order.getOrderTag().trim().isEmpty()) {
            order.setOrderTag(generateOrderTag());
        }
        
        // Set date if not provided
        if (order.getDate() == null) {
            order.setDate(LocalDate.now());
        }
        
        // Update product stock
        product.setProductUnitStock(product.getProductUnitStock() - orderDto.getOrderQuantity());
        productRepository.save(product);
        
        Order savedOrder = orderRepository.save(order);
        log.info("Created new order with ID: {}", savedOrder.getOrderId());
        
        return orderMapper.toDto(savedOrder);
    }
    
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        // Validate user exists
        User user = userRepository.findById(orderDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + orderDto.getUserId()));
        
        // Validate product exists
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + orderDto.getProductId()));
        
        // Validate barcode exists
        BarCode barCode = barCodeRepository.findById(orderDto.getBarCodeId())
                .orElseThrow(() -> new ResourceNotFoundException("BarCode not found with id: " + orderDto.getBarCodeId()));
        
        // Handle stock changes if quantity changed
        if (!existingOrder.getOrderQuantity().equals(orderDto.getOrderQuantity())) {
            int stockDifference = orderDto.getOrderQuantity() - existingOrder.getOrderQuantity();
            if (product.getProductUnitStock() < stockDifference) {
                throw new InsufficientStockException("Insufficient stock for quantity change");
            }
            product.setProductUnitStock(product.getProductUnitStock() - stockDifference);
            productRepository.save(product);
        }
        
        // Update fields
        existingOrder.setDate(orderDto.getDate());
        existingOrder.setOrderQuantity(orderDto.getOrderQuantity());
        existingOrder.setTotalAmount(orderDto.getTotalAmount());
        existingOrder.setOrderStatus(orderDto.getOrderStatus());
        existingOrder.setPaymentMethod(orderDto.getPaymentMethod());
        existingOrder.setCustomerFullName(orderDto.getCustomerFullName());
        existingOrder.setCustomerPhone(orderDto.getCustomerPhone());
        existingOrder.setCustomerEmail(orderDto.getCustomerEmail());
        existingOrder.setCustomerAddress(orderDto.getCustomerAddress());
        existingOrder.setUser(user);
        existingOrder.setProduct(product);
        existingOrder.setBarCode(barCode);
        
        Order savedOrder = orderRepository.save(existingOrder);
        log.info("Updated order with ID: {}", savedOrder.getOrderId());
        
        return orderMapper.toDto(savedOrder);
    }
    
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        
        // Restore product stock
        Product product = order.getProduct();
        product.setProductUnitStock(product.getProductUnitStock() + order.getOrderQuantity());
        productRepository.save(product);
        
        orderRepository.delete(order);
        log.info("Deleted order with ID: {}", order.getOrderId());
    }
    
    // Dashboard statistics methods
    public Long getTotalOrderCount() {
        return orderRepository.countTotalOrders();
    }
    
    public Long getTodayOrderCount() {
        return orderRepository.countTodayOrders();
    }
    
    public Double getTodaySales() {
        return orderRepository.sumTodaySales();
    }
    
    public Double getWeeklySales() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        return orderRepository.sumSalesBetweenDates(startDate, endDate);
    }
    
    public Double getMonthlySales() {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);
        return orderRepository.sumSalesBetweenDates(startDate, endDate);
    }
    
    public Double getTotalSales() {
        return orderRepository.sumTotalSales();
    }
    
    private String generateOrderTag() {
        long count = orderRepository.count() + 1;
        return String.format("ORD-%08d", count);
    }
}