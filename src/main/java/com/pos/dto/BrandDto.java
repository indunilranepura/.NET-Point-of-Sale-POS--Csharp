package com.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDto {
    
    private Long brandId;
    private String brandTag;
    
    @NotBlank(message = "Brand name is required")
    @Size(max = 100)
    private String brandName;
    
    private String brandDescription;
    private String brandStatus;
    private byte[] brandImage;
    
    @NotNull(message = "Vendor ID is required")
    private Long vendorId;
    
    // Additional field for display
    private String vendorName;
}