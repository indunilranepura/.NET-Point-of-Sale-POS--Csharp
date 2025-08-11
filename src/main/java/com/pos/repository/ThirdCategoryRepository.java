package com.pos.repository;

import com.pos.entity.ThirdCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThirdCategoryRepository extends JpaRepository<ThirdCategory, Long> {
    
    Optional<ThirdCategory> findByThirdCategoryName(String thirdCategoryName);
    
    boolean existsByThirdCategoryName(String thirdCategoryName);
    
    List<ThirdCategory> findBySecondCategory_SecondCategoryId(Long secondCategoryId);
    
    @Query("SELECT tc FROM ThirdCategory tc WHERE " +
           "LOWER(tc.thirdCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%')) OR " +
           "LOWER(tc.secondCategory.secondCategoryName) LIKE LOWER(CONCAT('%', :searchKey, '%'))")
    List<ThirdCategory> findBySearchKey(@Param("searchKey") String searchKey);
    
    @Query("SELECT COUNT(tc) FROM ThirdCategory tc")
    Long countTotalThirdCategories();
}