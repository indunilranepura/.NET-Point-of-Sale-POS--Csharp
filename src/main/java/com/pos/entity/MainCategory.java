package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "main_categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class MainCategory extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_category_id")
    private Long mainCategoryId;
    
    @NotBlank(message = "Main category name is required")
    @Size(max = 100)
    @Column(name = "main_category_name", nullable = false, unique = true)
    private String mainCategoryName;
    
    @Column(name = "main_category_description", columnDefinition = "TEXT")
    private String mainCategoryDescription;
    
    @Lob
    @Column(name = "main_category_image")
    private byte[] mainCategoryImage;
    
    @OneToMany(mappedBy = "mainCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SecondCategory> secondCategories;
}