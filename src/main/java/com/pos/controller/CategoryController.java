package com.pos.controller;

import com.pos.dto.CategoryDto;
import com.pos.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CategoryController {
    
    private final CategoryService categoryService;
    
    // Main Categories
    @GetMapping("/main")
    public ResponseEntity<List<CategoryDto>> getAllMainCategories(@RequestParam(required = false) String search) {
        List<CategoryDto> categories = categoryService.searchMainCategories(search);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/main/{id}")
    public ResponseEntity<CategoryDto> getMainCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.getMainCategoryById(id);
        return ResponseEntity.ok(category);
    }
    
    @PostMapping("/main")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createMainCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createMainCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    @PutMapping("/main/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> updateMainCategory(@PathVariable Long id, 
                                                        @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateMainCategory(id, categoryDto);
        return ResponseEntity.ok(updatedCategory);
    }
    
    @DeleteMapping("/main/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMainCategory(@PathVariable Long id) {
        categoryService.deleteMainCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    // Second Categories
    @GetMapping("/second")
    public ResponseEntity<List<CategoryDto>> getAllSecondCategories(@RequestParam(required = false) String search) {
        List<CategoryDto> categories = categoryService.searchSecondCategories(search);
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping("/second")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createSecondCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createSecondCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    // Third Categories
    @GetMapping("/third")
    public ResponseEntity<List<CategoryDto>> getAllThirdCategories(@RequestParam(required = false) String search) {
        List<CategoryDto> categories = categoryService.searchThirdCategories(search);
        return ResponseEntity.ok(categories);
    }
    
    @PostMapping("/third")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDto> createThirdCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = categoryService.createThirdCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }
    
    // Statistics
    @GetMapping("/stats/main/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getMainCategoryCount() {
        Long count = categoryService.getMainCategoryCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/second/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getSecondCategoryCount() {
        Long count = categoryService.getSecondCategoryCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/third/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getThirdCategoryCount() {
        Long count = categoryService.getThirdCategoryCount();
        return ResponseEntity.ok(count);
    }
}