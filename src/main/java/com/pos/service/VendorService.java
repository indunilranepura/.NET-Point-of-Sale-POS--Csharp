package com.pos.service;

import com.pos.dto.VendorDto;
import com.pos.entity.ThirdCategory;
import com.pos.entity.Vendor;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.DuplicateResourceException;
import com.pos.mapper.VendorMapper;
import com.pos.repository.ThirdCategoryRepository;
import com.pos.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VendorService {
    
    private final VendorRepository vendorRepository;
    private final ThirdCategoryRepository thirdCategoryRepository;
    private final VendorMapper vendorMapper;
    
    public List<VendorDto> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendorMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public List<VendorDto> searchVendors(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllVendors();
        }
        return vendorRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(vendorMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public VendorDto getVendorById(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        return vendorMapper.toDto(vendor);
    }
    
    public VendorDto createVendor(VendorDto vendorDto) {
        if (vendorRepository.existsByVendorName(vendorDto.getVendorName())) {
            throw new DuplicateResourceException("Vendor already exists with name: " + vendorDto.getVendorName());
        }
        
        ThirdCategory thirdCategory = thirdCategoryRepository.findById(vendorDto.getThirdCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Third category not found with id: " + vendorDto.getThirdCategoryId()));
        
        Vendor vendor = vendorMapper.toEntity(vendorDto);
        vendor.setThirdCategory(thirdCategory);
        
        if (vendor.getVendorTag() == null || vendor.getVendorTag().trim().isEmpty()) {
            vendor.setVendorTag(generateVendorTag());
        }
        
        if (vendor.getRegisterDate() == null) {
            vendor.setRegisterDate(LocalDate.now());
        }
        
        Vendor savedVendor = vendorRepository.save(vendor);
        log.info("Created new vendor with ID: {}", savedVendor.getVendorId());
        
        return vendorMapper.toDto(savedVendor);
    }
    
    public VendorDto updateVendor(Long id, VendorDto vendorDto) {
        Vendor existingVendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        
        if (!existingVendor.getVendorName().equals(vendorDto.getVendorName()) &&
            vendorRepository.existsByVendorName(vendorDto.getVendorName())) {
            throw new DuplicateResourceException("Vendor already exists with name: " + vendorDto.getVendorName());
        }
        
        ThirdCategory thirdCategory = thirdCategoryRepository.findById(vendorDto.getThirdCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Third category not found with id: " + vendorDto.getThirdCategoryId()));
        
        existingVendor.setVendorName(vendorDto.getVendorName());
        existingVendor.setVendorDescription(vendorDto.getVendorDescription());
        existingVendor.setVendorStatus(vendorDto.getVendorStatus());
        existingVendor.setVendorImage(vendorDto.getVendorImage());
        existingVendor.setRegisterDate(vendorDto.getRegisterDate());
        existingVendor.setThirdCategory(thirdCategory);
        
        Vendor savedVendor = vendorRepository.save(existingVendor);
        log.info("Updated vendor with ID: {}", savedVendor.getVendorId());
        
        return vendorMapper.toDto(savedVendor);
    }
    
    public void deleteVendor(Long id) {
        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
        
        vendorRepository.delete(vendor);
        log.info("Deleted vendor with ID: {}", vendor.getVendorId());
    }
    
    public Long getTotalVendorCount() {
        return vendorRepository.countTotalVendors();
    }
    
    private String generateVendorTag() {
        long count = vendorRepository.count() + 1;
        return String.format("V-%03d", count);
    }
}