package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.CategoryDTO;
import com.wexad.librarymanagement.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface CategoryMapper extends BaseMapper<CategoryDTO, Category> {
    CategoryMapper CATEGORY_MAPPER = Mappers.getMapper(CategoryMapper.class);
}
