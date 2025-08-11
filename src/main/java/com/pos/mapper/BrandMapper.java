package com.pos.mapper;

import com.pos.dto.BrandDto;
import com.pos.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    
    @Mapping(source = "vendor.vendorId", target = "vendorId")
    @Mapping(source = "vendor.vendorName", target = "vendorName")
    BrandDto toDto(Brand brand);
    
    @Mapping(target = "brandId", ignore = true)
    @Mapping(target = "vendor", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Brand toEntity(BrandDto brandDto);
}