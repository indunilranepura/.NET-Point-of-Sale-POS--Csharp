package com.pos.repository;

import com.pos.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Optional<Product> findByProductIdTag(String productIdTag);
    
    boolean existsByProductIdTag(String productIdTag);
    
    List<Product> findByBrand_BrandId(Long brandId);
    
    @Query("SELECT p FROM Product p WHERE " +
           "LOWER(p.productName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(p.productIdTag) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(p.brand.brandName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<Product> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(p) FROM Product p")
    Long countTotalProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE LOWER(p.productStatus) = 'yes'")
    Long countAvailableProducts();
    
    @Query("SELECT COUNT(p) FROM Product p WHERE LOWER(p.productStatus) = 'no'")
    Long countUnavailableProducts();
    
    @Query("SELECT p FROM Product p WHERE LOWER(p.productStatus) = 'yes'")
    List<Product> findAvailableProducts();
}