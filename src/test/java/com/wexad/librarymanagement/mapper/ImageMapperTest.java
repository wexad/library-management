package com.wexad.librarymanagement.mapper;

import static org.junit.jupiter.api.Assertions.*;

import com.wexad.librarymanagement.dto.ImageDTO;
import com.wexad.librarymanagement.entity.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class ImageMapperTest {

    private ImageMapper imageMapper;

    @BeforeEach
    public void setup() {
        imageMapper = Mappers.getMapper(ImageMapper.class);
    }

    @Test
    public void testToDto() {
        Image image = new Image();
        image.setPath("some/path/to/image.jpg");
        ImageDTO dto = imageMapper.toDto(image);
        assertNotNull(dto);
        assertEquals(image.getPath(), dto.getPath());
    }

    @Test
    public void testToEntity() {
        ImageDTO dto = new ImageDTO();
        dto.setPath("some/path/to/image.jpg");
        Image image = imageMapper.toEntity(dto);
        assertNotNull(image);
        assertEquals(dto.getPath(), image.getPath());
    }
}