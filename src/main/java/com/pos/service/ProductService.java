package com.pos.service;

import com.pos.dto.ProductDto;
import com.pos.entity.Brand;
import com.pos.entity.Product;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.DuplicateResourceException;
import com.pos.mapper.ProductMapper;
import com.pos.repository.BrandRepository;
import com.pos.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;
    
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<ProductDto> searchProducts(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllProducts();
        }
        return productRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<ProductDto> getAvailableProducts() {
        return productRepository.findAvailableProducts()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toDto(product);
    }
    
    public ProductDto createProduct(ProductDto productDto) {
        // Check if product tag already exists
        if (productDto.getProductIdTag() != null && 
            productRepository.existsByProductIdTag(productDto.getProductIdTag())) {
            throw new DuplicateResourceException("Product already exists with tag: " + productDto.getProductIdTag());
        }
        
        // Validate brand exists
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + productDto.getBrandId()));
        
        Product product = productMapper.toEntity(productDto);
        product.setBrand(brand);
        
        // Generate product tag if not provided
        if (product.getProductIdTag() == null || product.getProductIdTag().trim().isEmpty()) {
            product.setProductIdTag(generateProductTag());
        }
        
        Product savedProduct = productRepository.save(product);
        log.info("Created new product with ID: {}", savedProduct.getProductId());
        
        return productMapper.toDto(savedProduct);
    }
    
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        // Check product tag uniqueness if changed
        if (productDto.getProductIdTag() != null && 
            !existingProduct.getProductIdTag().equals(productDto.getProductIdTag()) &&
            productRepository.existsByProductIdTag(productDto.getProductIdTag())) {
            throw new DuplicateResourceException("Product already exists with tag: " + productDto.getProductIdTag());
        }
        
        // Validate brand exists
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + productDto.getBrandId()));
        
        // Update fields
        existingProduct.setProductName(productDto.getProductName());
        existingProduct.setProductDescription(productDto.getProductDescription());
        existingProduct.setProductQuantityPerUnit(productDto.getProductQuantityPerUnit());
        existingProduct.setProductPerUnitPrice(productDto.getProductPerUnitPrice());
        existingProduct.setProductMSRP(productDto.getProductMSRP());
        existingProduct.setProductStatus(productDto.getProductStatus());
        existingProduct.setProductDiscountRate(productDto.getProductDiscountRate());
        existingProduct.setProductSize(productDto.getProductSize());
        existingProduct.setProductColor(productDto.getProductColor());
        existingProduct.setProductWeight(productDto.getProductWeight());
        existingProduct.setProductUnitStock(productDto.getProductUnitStock());
        existingProduct.setBrand(brand);
        
        if (productDto.getProductIdTag() != null) {
            existingProduct.setProductIdTag(productDto.getProductIdTag());
        }
        
        Product savedProduct = productRepository.save(existingProduct);
        log.info("Updated product with ID: {}", savedProduct.getProductId());
        
        return productMapper.toDto(savedProduct);
    }
    
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        productRepository.delete(product);
        log.info("Deleted product with ID: {}", product.getProductId());
    }
    
    // Dashboard statistics methods
    public Long getTotalProductCount() {
        return productRepository.countTotalProducts();
    }
    
    public Long getAvailableProductCount() {
        return productRepository.countAvailableProducts();
    }
    
    public Long getUnavailableProductCount() {
        return productRepository.countUnavailableProducts();
    }
    
    private String generateProductTag() {
        long count = productRepository.count() + 1;
        return String.format("P-%06d", count);
    }
}