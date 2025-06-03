package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.LoanDTO;
import com.wexad.librarymanagement.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper extends BaseMapper<LoanDTO, Loan> {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "user.name", target = "username")
    @Mapping(source = "book.title", target = "title")
    @Mapping(source = "updatedAt", target = "borrowedAt")
    @Override
    LoanDTO toDto(Loan entity);
}
