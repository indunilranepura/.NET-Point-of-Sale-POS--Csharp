package com.pos.repository;

import com.pos.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByVendorName(String vendorName);
    
    boolean existsByVendorName(String vendorName);
    
    List<Vendor> findByThirdCategory_ThirdCategoryId(Long thirdCategoryId);
    
    @Query("SELECT v FROM Vendor v WHERE " +
           "LOWER(v.vendorName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(v.vendorStatus) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(v.thirdCategory.thirdCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<Vendor> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(v) FROM Vendor v")
    Long countTotalVendors();
}