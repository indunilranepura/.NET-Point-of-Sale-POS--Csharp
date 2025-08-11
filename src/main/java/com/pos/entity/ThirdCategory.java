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
@Table(name = "third_categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ThirdCategory extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "third_category_id")
    private Long thirdCategoryId;
    
    @NotBlank(message = "Third category name is required")
    @Size(max = 100)
    @Column(name = "third_category_name", nullable = false)
    private String thirdCategoryName;
    
    @Column(name = "third_category_description", columnDefinition = "TEXT")
    private String thirdCategoryDescription;
    
    @Lob
    @Column(name = "third_category_image")
    private byte[] thirdCategoryImage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_category_id", nullable = false)
    private SecondCategory secondCategory;
    
    @OneToMany(mappedBy = "thirdCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vendor> vendors;
}