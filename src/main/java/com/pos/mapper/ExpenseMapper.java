package com.pos.mapper;

import com.pos.dto.ExpenseDto;
import com.pos.entity.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {
    
    ExpenseDto toDto(Expense expense);
    
    @Mapping(target = "expenseId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Expense toEntity(ExpenseDto expenseDto);
}