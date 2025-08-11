package com.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDto {
    
    private Long vendorId;
    private String vendorTag;
    
    @NotBlank(message = "Vendor name is required")
    @Size(max = 100)
    private String vendorName;
    
    private String vendorDescription;
    private String vendorStatus;
    private byte[] vendorImage;
    private LocalDate registerDate;
    
    @NotNull(message = "Third category ID is required")
    private Long thirdCategoryId;
    
    // Additional field for display
    private String thirdCategoryName;
}