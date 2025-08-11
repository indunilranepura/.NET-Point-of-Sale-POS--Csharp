package com.pos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    
    private Long id;
    
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;
    
    private String description;
    private byte[] image;
    
    // For hierarchical categories
    private Long parentId;
    private String parentName;
}