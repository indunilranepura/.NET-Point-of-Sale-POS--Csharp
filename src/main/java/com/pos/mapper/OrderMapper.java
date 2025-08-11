package com.pos.mapper;

import com.pos.dto.OrderDto;
import com.pos.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.productName", target = "productName")
    @Mapping(source = "product.productPerUnitPrice", target = "productPerUnitPrice")
    @Mapping(source = "barCode.barCodeId", target = "barCodeId")
    OrderDto toDto(Order order);
    
    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "barCode", ignore = true)
    @Mapping(target = "orderProductMaps", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderDto orderDto);
}