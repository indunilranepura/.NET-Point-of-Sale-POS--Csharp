package com.pos.controller;

import com.pos.dto.BrandDto;
import com.pos.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class BrandController {
    
    private final BrandService brandService;
    
    @GetMapping
    public ResponseEntity<List<BrandDto>> getAllBrands(@RequestParam(required = false) String search) {
        List<BrandDto> brands = brandService.searchBrands(search);
        return ResponseEntity.ok(brands);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BrandDto> getBrandById(@PathVariable Long id) {
        BrandDto brand = brandService.getBrandById(id);
        return ResponseEntity.ok(brand);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDto> createBrand(@Valid @RequestBody BrandDto brandDto) {
        BrandDto createdBrand = brandService.createBrand(brandDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBrand);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BrandDto> updateBrand(@PathVariable Long id, 
                                              @Valid @RequestBody BrandDto brandDto) {
        BrandDto updatedBrand = brandService.updateBrand(id, brandDto);
        return ResponseEntity.ok(updatedBrand);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalBrandCount() {
        Long count = brandService.getTotalBrandCount();
        return ResponseEntity.ok(count);
    }
}