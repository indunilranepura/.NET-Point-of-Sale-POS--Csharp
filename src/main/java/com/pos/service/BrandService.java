package com.pos.service;

import com.pos.dto.BrandDto;
import com.pos.entity.Brand;
import com.pos.entity.Vendor;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.DuplicateResourceException;
import com.pos.mapper.BrandMapper;
import com.pos.repository.BrandRepository;
import com.pos.repository.VendorRepository;
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
public class BrandService {
    
    private final BrandRepository brandRepository;
    private final VendorRepository vendorRepository;
    private final BrandMapper brandMapper;
    
    public List<BrandDto> getAllBrands() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<BrandDto> searchBrands(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllBrands();
        }
        return brandRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public BrandDto getBrandById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return brandMapper.toDto(brand);
    }
    
    public BrandDto createBrand(BrandDto brandDto) {
        if (brandRepository.existsByBrandName(brandDto.getBrandName())) {
            throw new DuplicateResourceException("Brand already exists with name: " + brandDto.getBrandName());
        }
        
        Vendor vendor = vendorRepository.findById(brandDto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + brandDto.getVendorId()));
        
        Brand brand = brandMapper.toEntity(brandDto);
        brand.setVendor(vendor);
        
        if (brand.getBrandTag() == null || brand.getBrandTag().trim().isEmpty()) {
            brand.setBrandTag(generateBrandTag());
        }
        
        Brand savedBrand = brandRepository.save(brand);
        log.info("Created new brand with ID: {}", savedBrand.getBrandId());
        
        return brandMapper.toDto(savedBrand);
    }
    
    public BrandDto updateBrand(Long id, BrandDto brandDto) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        
        if (!existingBrand.getBrandName().equals(brandDto.getBrandName()) &&
            brandRepository.existsByBrandName(brandDto.getBrandName())) {
            throw new DuplicateResourceException("Brand already exists with name: " + brandDto.getBrandName());
        }
        
        Vendor vendor = vendorRepository.findById(brandDto.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + brandDto.getVendorId()));
        
        existingBrand.setBrandName(brandDto.getBrandName());
        existingBrand.setBrandDescription(brandDto.getBrandDescription());
        existingBrand.setBrandStatus(brandDto.getBrandStatus());
        existingBrand.setBrandImage(brandDto.getBrandImage());
        existingBrand.setVendor(vendor);
        
        Brand savedBrand = brandRepository.save(existingBrand);
        log.info("Updated brand with ID: {}", savedBrand.getBrandId());
        
        return brandMapper.toDto(savedBrand);
    }
    
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        
        brandRepository.delete(brand);
        log.info("Deleted brand with ID: {}", brand.getBrandId());
    }
    
    public Long getTotalBrandCount() {
        return brandRepository.countTotalBrands();
    }
    
    private String generateBrandTag() {
        long count = brandRepository.count() + 1;
        return String.format("B-%03d", count);
    }
}