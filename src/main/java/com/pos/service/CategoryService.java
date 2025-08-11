package com.pos.service;

import com.pos.dto.CategoryDto;
import com.pos.entity.MainCategory;
import com.pos.entity.SecondCategory;
import com.pos.entity.ThirdCategory;
import com.pos.exception.ResourceNotFoundException;
import com.pos.exception.DuplicateResourceException;
import com.pos.mapper.CategoryMapper;
import com.pos.repository.MainCategoryRepository;
import com.pos.repository.SecondCategoryRepository;
import com.pos.repository.ThirdCategoryRepository;
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
public class CategoryService {
    
    private final MainCategoryRepository mainCategoryRepository;
    private final SecondCategoryRepository secondCategoryRepository;
    private final ThirdCategoryRepository thirdCategoryRepository;
    private final CategoryMapper categoryMapper;
    
    // Main Category methods
    public List<CategoryDto> getAllMainCategories() {
        return mainCategoryRepository.findAll()
                .stream()
                .map(categoryMapper::mainCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public List<CategoryDto> searchMainCategories(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllMainCategories();
        }
        return mainCategoryRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(categoryMapper::mainCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public CategoryDto getMainCategoryById(Long id) {
        MainCategory mainCategory = mainCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Main category not found with id: " + id));
        return categoryMapper.mainCategoryToDto(mainCategory);
    }
    
    public CategoryDto createMainCategory(CategoryDto categoryDto) {
        if (mainCategoryRepository.existsByMainCategoryName(categoryDto.getName())) {
            throw new DuplicateResourceException("Main category already exists with name: " + categoryDto.getName());
        }
        
        MainCategory mainCategory = categoryMapper.dtoToMainCategory(categoryDto);
        MainCategory savedCategory = mainCategoryRepository.save(mainCategory);
        log.info("Created new main category with ID: {}", savedCategory.getMainCategoryId());
        
        return categoryMapper.mainCategoryToDto(savedCategory);
    }
    
    public CategoryDto updateMainCategory(Long id, CategoryDto categoryDto) {
        MainCategory existingCategory = mainCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Main category not found with id: " + id));
        
        if (!existingCategory.getMainCategoryName().equals(categoryDto.getName()) &&
            mainCategoryRepository.existsByMainCategoryName(categoryDto.getName())) {
            throw new DuplicateResourceException("Main category already exists with name: " + categoryDto.getName());
        }
        
        existingCategory.setMainCategoryName(categoryDto.getName());
        existingCategory.setMainCategoryDescription(categoryDto.getDescription());
        existingCategory.setMainCategoryImage(categoryDto.getImage());
        
        MainCategory savedCategory = mainCategoryRepository.save(existingCategory);
        log.info("Updated main category with ID: {}", savedCategory.getMainCategoryId());
        
        return categoryMapper.mainCategoryToDto(savedCategory);
    }
    
    public void deleteMainCategory(Long id) {
        MainCategory mainCategory = mainCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Main category not found with id: " + id));
        
        mainCategoryRepository.delete(mainCategory);
        log.info("Deleted main category with ID: {}", mainCategory.getMainCategoryId());
    }
    
    // Second Category methods
    public List<CategoryDto> getAllSecondCategories() {
        return secondCategoryRepository.findAll()
                .stream()
                .map(categoryMapper::secondCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public List<CategoryDto> searchSecondCategories(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllSecondCategories();
        }
        return secondCategoryRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(categoryMapper::secondCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public CategoryDto createSecondCategory(CategoryDto categoryDto) {
        if (secondCategoryRepository.existsBySecondCategoryName(categoryDto.getName())) {
            throw new DuplicateResourceException("Second category already exists with name: " + categoryDto.getName());
        }
        
        MainCategory mainCategory = mainCategoryRepository.findById(categoryDto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Main category not found with id: " + categoryDto.getParentId()));
        
        SecondCategory secondCategory = categoryMapper.dtoToSecondCategory(categoryDto);
        secondCategory.setMainCategory(mainCategory);
        
        SecondCategory savedCategory = secondCategoryRepository.save(secondCategory);
        log.info("Created new second category with ID: {}", savedCategory.getSecondCategoryId());
        
        return categoryMapper.secondCategoryToDto(savedCategory);
    }
    
    // Third Category methods
    public List<CategoryDto> getAllThirdCategories() {
        return thirdCategoryRepository.findAll()
                .stream()
                .map(categoryMapper::thirdCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public List<CategoryDto> searchThirdCategories(String searchKey) {
        if (searchKey == null || searchKey.trim().isEmpty()) {
            return getAllThirdCategories();
        }
        return thirdCategoryRepository.findBySearchKey(searchKey.trim())
                .stream()
                .map(categoryMapper::thirdCategoryToDto)
                .collect(Collectors.toList());
    }
    
    public CategoryDto createThirdCategory(CategoryDto categoryDto) {
        if (thirdCategoryRepository.existsByThirdCategoryName(categoryDto.getName())) {
            throw new DuplicateResourceException("Third category already exists with name: " + categoryDto.getName());
        }
        
        SecondCategory secondCategory = secondCategoryRepository.findById(categoryDto.getParentId())
                .orElseThrow(() -> new ResourceNotFoundException("Second category not found with id: " + categoryDto.getParentId()));
        
        ThirdCategory thirdCategory = categoryMapper.dtoToThirdCategory(categoryDto);
        thirdCategory.setSecondCategory(secondCategory);
        
        ThirdCategory savedCategory = thirdCategoryRepository.save(thirdCategory);
        log.info("Created new third category with ID: {}", savedCategory.getThirdCategoryId());
        
        return categoryMapper.thirdCategoryToDto(savedCategory);
    }
    
    // Dashboard statistics methods
    public Long getMainCategoryCount() {
        return mainCategoryRepository.countTotalMainCategories();
    }
    
    public Long getSecondCategoryCount() {
        return secondCategoryRepository.countTotalSecondCategories();
    }
    
    public Long getThirdCategoryCount() {
        return thirdCategoryRepository.countTotalThirdCategories();
    }
}