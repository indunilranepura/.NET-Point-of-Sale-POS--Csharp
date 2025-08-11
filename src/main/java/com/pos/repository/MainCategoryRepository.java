package com.pos.repository;

import com.pos.entity.MainCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long> {
    
    Optional<MainCategory> findByMainCategoryName(String mainCategoryName);
    
    boolean existsByMainCategoryName(String mainCategoryName);
    
    @Query("SELECT mc FROM MainCategory mc WHERE " +
           "LOWER(mc.mainCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<MainCategory> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(mc) FROM MainCategory mc")
    Long countTotalMainCategories();
}