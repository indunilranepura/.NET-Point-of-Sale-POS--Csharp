package com.pos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "vendors")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Vendor extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vendor_id")
    private Long vendorId;
    
    @Column(name = "vendor_tag", unique = true)
    private String vendorTag;
    
    @NotBlank(message = "Vendor name is required")
    @Size(max = 100)
    @Column(name = "vendor_name", nullable = false)
    private String vendorName;
    
    @Column(name = "vendor_description", columnDefinition = "TEXT")
    private String vendorDescription;
    
    @Column(name = "vendor_status")
    private String vendorStatus;
    
    @Lob
    @Column(name = "vendor_image")
    private byte[] vendorImage;
    
    @Column(name = "register_date")
    private LocalDate registerDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "third_category_id", nullable = false)
    private ThirdCategory thirdCategory;
    
    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Brand> brands;
}