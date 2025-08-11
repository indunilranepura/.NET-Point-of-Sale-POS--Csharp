package com.pos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    
    private Long orderId;
    private String orderTag;
    
    @NotNull(message = "Date is required")
    private LocalDate date;
    
    @Min(value = 1, message = "Order quantity must be at least 1")
    private Integer orderQuantity;
    
    @DecimalMin(value = "0.0", message = "Total amount must be positive")
    private Double totalAmount;
    
    private String orderStatus;
    private String paymentMethod;
    
    // Customer Information
    @Size(max = 100)
    private String customerFullName;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    private String customerPhone;
    
    @Email(message = "Email should be valid")
    private String customerEmail;
    
    private String customerAddress;
    
    // Foreign Keys
    @NotNull(message = "User ID is required")
    private Long userId;
    
    @NotNull(message = "Product ID is required")
    private Long productId;
    
    @NotNull(message = "Bar code ID is required")
    private Long barCodeId;
    
    // Additional fields for display
    private String userFirstName;
    private String userLastName;
    private String productName;
    private Double productPerUnitPrice;
}