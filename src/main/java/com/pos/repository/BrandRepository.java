package com.pos.repository;

import com.pos.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    
    Optional<Brand> findByBrandName(String brandName);
    
    boolean existsByBrandName(String brandName);
    
    List<Brand> findByVendor_VendorId(Long vendorId);
    
    @Query("SELECT b FROM Brand b WHERE " +
           "LOWER(b.brandName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(b.brandStatus) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(b.vendor.vendorName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<Brand> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(b) FROM Brand b")
    Long countTotalBrands();
}