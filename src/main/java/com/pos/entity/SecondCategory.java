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
@Table(name = "second_categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SecondCategory extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "second_category_id")
    private Long secondCategoryId;
    
    @NotBlank(message = "Second category name is required")
    @Size(max = 100)
    @Column(name = "second_category_name", nullable = false)
    private String secondCategoryName;
    
    @Column(name = "second_category_description", columnDefinition = "TEXT")
    private String secondCategoryDescription;
    
    @Lob
    @Column(name = "second_category_image")
    private byte[] secondCategoryImage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_category_id", nullable = false)
    private MainCategory mainCategory;
    
    @OneToMany(mappedBy = "secondCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ThirdCategory> thirdCategories;
}