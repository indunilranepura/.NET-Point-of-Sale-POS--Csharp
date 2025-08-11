package com.pos.repository;

import com.pos.entity.BarCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarCodeRepository extends JpaRepository<BarCode, Long> {
    
    Optional<BarCode> findByBarCode(String barCode);
    
    boolean existsByBarCode(String barCode);
}