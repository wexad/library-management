package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.BookDTO;
import com.wexad.librarymanagement.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, ImageMapper.class})
public interface BookMapper extends BaseMapper<BookDTO, Book> {
}
