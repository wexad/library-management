package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.CategoryDTO;
import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.entity.Image;
import com.wexad.librarymanagement.mapper.CategoryMapper;
import com.wexad.librarymanagement.repository.CategoryRepository;
import com.wexad.librarymanagement.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void testFindAll() {
        List<Category> mockCategories = List.of(new Category(), new Category());
        List<CategoryDTO> mockDTOs = List.of(new CategoryDTO(), new CategoryDTO());

        when(categoryRepository.findAll()).thenReturn(mockCategories);
        when(categoryMapper.toDtoList(mockCategories)).thenReturn(mockDTOs);

        List<CategoryDTO> result = categoryService.findAll();

        assertEquals(2, result.size());
        verify(categoryRepository).findAll();
        verify(categoryMapper).toDtoList(mockCategories);
    }

    @Test
    void testFindById_found() {
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);

        CategoryDTO result = categoryService.findById(1L);

        assertNotNull(result);
        verify(categoryRepository).findById(1L);
        verify(categoryMapper).toDto(category);
    }

    @Test
    void testFindById_notFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> categoryService.findById(1L));
        assertEquals("Category not found", ex.getMessage());
    }

    @Test
    void testDelete() {
        categoryService.delete(1L);
        verify(categoryRepository).softDeleteById(1L);
    }

    @Test
    void testRestore() {
        categoryService.restore(1L);
        verify(categoryRepository).restoreById(1L);
    }

    @Test
    void testSave_whenImageUploadSuccess() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Image image = Image.builder().path("/images/category/test.jpg").build();
        Category savedCategory = Category.builder().name("Science").image(image).build();

        when(file.getOriginalFilename()).thenReturn("test.jpg");
        doNothing().when(file).transferTo(any(File.class));
        when(imageRepository.save(any(Image.class))).thenReturn(image);
        when(categoryRepository.save(any(Category.class))).thenReturn(savedCategory);

        categoryService.save("Science", file);

        verify(imageRepository).save(any(Image.class));
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_whenImageProvided() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        Image newImage = Image.builder().path("/images/category/updated.jpg").build();
        Category existing = Category.builder().name("Old").image(newImage).build();
        Category updated = Category.builder().name("New").build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(file.getOriginalFilename()).thenReturn("new.jpg");
        doNothing().when(file).transferTo(any(File.class));
        when(imageRepository.save(any(Image.class))).thenReturn(newImage);

        categoryService.updateCategory(1L, updated, file);

        assertEquals("New", existing.getName());
        verify(categoryRepository).save(existing);
    }

    @Test
    void testFindAllDeleted() {
        List<Category> deleted = List.of(new Category());
        List<CategoryDTO> dtoList = List.of(new CategoryDTO());

        when(categoryRepository.findAllDeleted()).thenReturn(deleted);
        when(categoryMapper.toDtoList(deleted)).thenReturn(dtoList);

        List<CategoryDTO> result = categoryService.findAllDeleted();

        assertEquals(1, result.size());
        verify(categoryRepository).findAllDeleted();
        verify(categoryMapper).toDtoList(deleted);
    }
}
