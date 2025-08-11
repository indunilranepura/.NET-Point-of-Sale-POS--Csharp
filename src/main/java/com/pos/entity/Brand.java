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
@Table(name = "brands")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Brand extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long brandId;
    
    @Column(name = "brand_tag", unique = true)
    private String brandTag;
    
    @NotBlank(message = "Brand name is required")
    @Size(max = 100)
    @Column(name = "brand_name", nullable = false)
    private String brandName;
    
    @Column(name = "brand_description", columnDefinition = "TEXT")
    private String brandDescription;
    
    @Column(name = "brand_status")
    private String brandStatus;
    
    @Lob
    @Column(name = "brand_image")
    private byte[] brandImage;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private Vendor vendor;
    
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}