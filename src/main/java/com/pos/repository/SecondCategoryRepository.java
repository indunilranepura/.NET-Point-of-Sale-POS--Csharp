package com.pos.repository;

import com.pos.entity.SecondCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SecondCategoryRepository extends JpaRepository<SecondCategory, Long> {
    
    Optional<SecondCategory> findBySecondCategoryName(String secondCategoryName);
    
    boolean existsBySecondCategoryName(String secondCategoryName);
    
    List<SecondCategory> findByMainCategory_MainCategoryId(Long mainCategoryId);
    
    @Query("SELECT sc FROM SecondCategory sc WHERE " +
           "LOWER(sc.secondCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(sc.mainCategory.mainCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<SecondCategory> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(sc) FROM SecondCategory sc")
    Long countTotalSecondCategories();
}