package com.wexad.librarymanagement.mapper;

import com.wexad.librarymanagement.dto.ImageDTO;
import com.wexad.librarymanagement.entity.Image;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImageMapper extends BaseMapper<ImageDTO, Image> {
    ImageMapper IMAGE_MAPPER = Mappers.getMapper(ImageMapper.class);
}
