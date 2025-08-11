package com.pos.mapper;

import com.pos.dto.VendorDto;
import com.pos.entity.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VendorMapper {
    
    @Mapping(source = "thirdCategory.thirdCategoryId", target = "thirdCategoryId")
    @Mapping(source = "thirdCategory.thirdCategoryName", target = "thirdCategoryName")
    VendorDto toDto(Vendor vendor);
    
    @Mapping(target = "vendorId", ignore = true)
    @Mapping(target = "thirdCategory", ignore = true)
    @Mapping(target = "brands", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Vendor toEntity(VendorDto vendorDto);
}