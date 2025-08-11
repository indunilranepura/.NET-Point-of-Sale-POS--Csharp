package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "order_tag", unique = true)
    private String orderTag;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Min(value = 1, message = "Order quantity must be at least 1")
    @Column(name = "order_quantity", nullable = false)
    private Integer orderQuantity;
    
    @DecimalMin(value = "0.0", message = "Total amount must be positive")
    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;
    
    @Column(name = "order_status")
    private String orderStatus;
    
    @Column(name = "payment_method")
    private String paymentMethod;
    
    // Customer Information
    @Size(max = 100)
    @Column(name = "customer_full_name")
    private String customerFullName;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Phone number should be valid")
    @Column(name = "customer_phone")
    private String customerPhone;
    
    @Email(message = "Email should be valid")
    @Column(name = "customer_email")
    private String customerEmail;
    
    @Column(name = "customer_address", columnDefinition = "TEXT")
    private String customerAddress;
    
    // Foreign Keys
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_code_id", nullable = false)
    private BarCode barCode;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProductMap> orderProductMaps;
}