package com.pos.mapper;

import com.pos.dto.ProductDto;
import com.pos.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    
    @Mapping(source = "brand.brandId", target = "brandId")
    @Mapping(source = "brand.brandName", target = "brandName")
    @Mapping(source = "brand.vendor.vendorName", target = "vendorName")
    @Mapping(source = "brand.vendor.thirdCategory.thirdCategoryName", target = "thirdCategoryName")
    @Mapping(source = "brand.vendor.thirdCategory.secondCategory.secondCategoryName", target = "secondCategoryName")
    @Mapping(source = "brand.vendor.thirdCategory.secondCategory.mainCategory.mainCategoryName", target = "mainCategoryName")
    ProductDto toDto(Product product);
    
    @Mapping(target = "productId", ignore = true)
    @Mapping(target = "brand", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "orderProductMaps", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product toEntity(ProductDto productDto);
}