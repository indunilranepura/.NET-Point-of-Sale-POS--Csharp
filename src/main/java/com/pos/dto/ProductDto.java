package com.pos.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    
    private Long productId;
    private String productIdTag;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 200)
    private String productName;
    
    private String productDescription;
    
    @DecimalMin(value = "0.0", message = "Quantity per unit must be positive")
    private Double productQuantityPerUnit;
    
    @DecimalMin(value = "0.0", message = "Per unit price must be positive")
    private Double productPerUnitPrice;
    
    @DecimalMin(value = "0.0", message = "MSRP must be positive")
    private Double productMSRP;
    
    private String productStatus;
    
    @DecimalMin(value = "0.0", message = "Discount rate must be positive")
    @DecimalMax(value = "100.0", message = "Discount rate cannot exceed 100%")
    private Double productDiscountRate;
    
    @DecimalMin(value = "0.0", message = "Size must be positive")
    private Double productSize;
    
    @Size(max = 50)
    private String productColor;
    
    @DecimalMin(value = "0.0", message = "Weight must be positive")
    private Double productWeight;
    
    @Min(value = 0, message = "Unit stock must be non-negative")
    private Integer productUnitStock;
    
    @NotNull(message = "Brand ID is required")
    private Long brandId;
    
    // Additional fields for display
    private String brandName;
    private String vendorName;
    private String thirdCategoryName;
    private String secondCategoryName;
    private String mainCategoryName;
}