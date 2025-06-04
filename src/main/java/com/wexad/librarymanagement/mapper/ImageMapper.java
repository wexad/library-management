package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ImageDTO;
import com.wexad.librarymanagement.entity.Image;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageMapper extends BaseMapper<ImageDTO, Image> {
}
