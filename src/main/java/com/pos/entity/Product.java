package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    
    @Column(name = "product_id_tag", unique = true)
    private String productIdTag;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 200)
    @Column(name = "product_name", nullable = false)
    private String productName;
    
    @Column(name = "product_description", columnDefinition = "TEXT")
    private String productDescription;
    
    @DecimalMin(value = "0.0", message = "Quantity per unit must be positive")
    @Column(name = "product_quantity_per_unit")
    private Double productQuantityPerUnit;
    
    @DecimalMin(value = "0.0", message = "Per unit price must be positive")
    @Column(name = "product_per_unit_price")
    private Double productPerUnitPrice;
    
    @DecimalMin(value = "0.0", message = "MSRP must be positive")
    @Column(name = "product_msrp")
    private Double productMSRP;
    
    @Column(name = "product_status")
    private String productStatus;
    
    @DecimalMin(value = "0.0", message = "Discount rate must be positive")
    @DecimalMax(value = "100.0", message = "Discount rate cannot exceed 100%")
    @Column(name = "product_discount_rate")
    private Double productDiscountRate;
    
    @DecimalMin(value = "0.0", message = "Size must be positive")
    @Column(name = "product_size")
    private Double productSize;
    
    @Size(max = 50)
    @Column(name = "product_color")
    private String productColor;
    
    @DecimalMin(value = "0.0", message = "Weight must be positive")
    @Column(name = "product_weight")
    private Double productWeight;
    
    @Min(value = 0, message = "Unit stock must be non-negative")
    @Column(name = "product_unit_stock")
    private Integer productUnitStock;
    
    @Lob
    @Column(name = "product_pictures")
    private byte[] productPictures;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProductMap> orderProductMaps;
}