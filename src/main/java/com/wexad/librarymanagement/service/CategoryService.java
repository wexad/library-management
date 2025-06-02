package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.CategoryDTO;
import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.entity.Image;
import com.wexad.librarymanagement.mapper.CategoryMapper;
import com.wexad.librarymanagement.repository.CategoryRepository;
import com.wexad.librarymanagement.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public CategoryService(CategoryMapper categoryMapper, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public Image uploadFile(MultipartFile imageFile) throws IOException {
        String uploadDir = "/home/wexad/upload-dir/images/category";
        Path dir = Paths.get(uploadDir);
        Files.createDirectories(dir);
        String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = dir.resolve(filename);
        imageFile.transferTo(filePath.toFile());

        Image newImage = Image.builder()
                .path("/images/category/" + filename)
                .build();
        imageRepository.save(newImage);
        return newImage;
    }

    public void save(String name, MultipartFile file) {
        try {
            Image image = uploadFile(file);
            Category category = Category.builder()
                    .name(name)
                    .image(image)
                    .build();
            categoryRepository.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Error in saving image: " + e.getMessage());
        }
    }

    public List<CategoryDTO> findAll() {
        // list of category dto
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    public List<CategoryDTO> findAllDeleted() {
        return categoryMapper.toDtoList(categoryRepository.findAllDeleted());
        // list of category dto
    }

    public void updateCategory(Long id, Category updatedCategory, MultipartFile imageFile) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        existingCategory.setName(updatedCategory.getName());
        Image newImage;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                newImage = uploadFile(imageFile);

                existingCategory.setImage(newImage);
            } catch (IOException e) {
                throw new RuntimeException("Error in saving image: " + e.getMessage());
            }
        }

        categoryRepository.save(existingCategory);
    }

    public CategoryDTO findById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found")));
        // category(id, name, image(path)) dto
    }

    public void delete(Long id) {
        categoryRepository.softDeleteById(id);
    }

    public void restore(Long id) {
        categoryRepository.restoreById(id);
    }
}
