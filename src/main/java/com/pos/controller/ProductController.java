package com.pos.controller;

import com.pos.dto.ProductDto;
import com.pos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ProductController {
    
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false) String search) {
        List<ProductDto> products = productService.searchProducts(search);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/available")
    public ResponseEntity<List<ProductDto>> getAvailableProducts() {
        List<ProductDto> products = productService.getAvailableProducts();
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        ProductDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto createdProduct = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, 
                                                  @Valid @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(id, productDto);
        return ResponseEntity.ok(updatedProduct);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/stats/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getTotalProductCount() {
        Long count = productService.getTotalProductCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/available")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getAvailableProductCount() {
        Long count = productService.getAvailableProductCount();
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/stats/unavailable")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Long> getUnavailableProductCount() {
        Long count = productService.getUnavailableProductCount();
        return ResponseEntity.ok(count);
    }
}