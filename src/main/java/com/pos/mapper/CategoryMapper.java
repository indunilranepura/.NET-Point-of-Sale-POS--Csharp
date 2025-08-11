package com.pos.mapper;

import com.pos.dto.CategoryDto;
import com.pos.entity.MainCategory;
import com.pos.entity.SecondCategory;
import com.pos.entity.ThirdCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    
    // Main Category mappings
    @Mapping(source = "mainCategoryId", target = "id")
    @Mapping(source = "mainCategoryName", target = "name")
    @Mapping(source = "mainCategoryDescription", target = "description")
    @Mapping(source = "mainCategoryImage", target = "image")
    @Mapping(target = "parentId", ignore = true)
    @Mapping(target = "parentName", ignore = true)
    CategoryDto mainCategoryToDto(MainCategory mainCategory);
    
    @Mapping(source = "name", target = "mainCategoryName")
    @Mapping(source = "description", target = "mainCategoryDescription")
    @Mapping(source = "image", target = "mainCategoryImage")
    @Mapping(target = "mainCategoryId", ignore = true)
    @Mapping(target = "secondCategories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    MainCategory dtoToMainCategory(CategoryDto categoryDto);
    
    // Second Category mappings
    @Mapping(source = "secondCategoryId", target = "id")
    @Mapping(source = "secondCategoryName", target = "name")
    @Mapping(source = "secondCategoryDescription", target = "description")
    @Mapping(source = "secondCategoryImage", target = "image")
    @Mapping(source = "mainCategory.mainCategoryId", target = "parentId")
    @Mapping(source = "mainCategory.mainCategoryName", target = "parentName")
    CategoryDto secondCategoryToDto(SecondCategory secondCategory);
    
    @Mapping(source = "name", target = "secondCategoryName")
    @Mapping(source = "description", target = "secondCategoryDescription")
    @Mapping(source = "image", target = "secondCategoryImage")
    @Mapping(target = "secondCategoryId", ignore = true)
    @Mapping(target = "mainCategory", ignore = true)
    @Mapping(target = "thirdCategories", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    SecondCategory dtoToSecondCategory(CategoryDto categoryDto);
    
    // Third Category mappings
    @Mapping(source = "thirdCategoryId", target = "id")
    @Mapping(source = "thirdCategoryName", target = "name")
    @Mapping(source = "thirdCategoryDescription", target = "description")
    @Mapping(source = "thirdCategoryImage", target = "image")
    @Mapping(source = "secondCategory.secondCategoryId", target = "parentId")
    @Mapping(source = "secondCategory.secondCategoryName", target = "parentName")
    CategoryDto thirdCategoryToDto(ThirdCategory thirdCategory);
    
    @Mapping(source = "name", target = "thirdCategoryName")
    @Mapping(source = "description", target = "thirdCategoryDescription")
    @Mapping(source = "image", target = "thirdCategoryImage")
    @Mapping(target = "thirdCategoryId", ignore = true)
    @Mapping(target = "secondCategory", ignore = true)
    @Mapping(target = "vendors", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ThirdCategory dtoToThirdCategory(CategoryDto categoryDto);
}