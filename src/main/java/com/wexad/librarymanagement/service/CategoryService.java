package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.entity.Image;
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

    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public CategoryService(CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public void save(String name, MultipartFile file) {
        try {
            String uploadDir = "/home/wexad/upload-dir/images/category";
            Files.createDirectories(Paths.get(uploadDir));
            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir).resolve(filename);
            file.transferTo(filePath.toFile());
            Image image = Image.builder()
                    .path("/images/category/" + filename)
                    .build();
            imageRepository.save(image);
            Category category = Category.builder()
                    .name(name)
                    .image(image)
                    .build();
            categoryRepository.save(category);
        } catch (IOException e) {
            throw new RuntimeException("Error in saving image: " + e.getMessage());
        }
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public List<Category> findAllDeleted() {
        return categoryRepository.findAllDeleted();
    }

    public void updateCategory(Long id, Category updatedCategory, MultipartFile imageFile) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setName(updatedCategory.getName());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = "/home/wexad/upload-dir/images/category";
                Files.createDirectories(Paths.get(uploadDir));
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir).resolve(filename);
                imageFile.transferTo(filePath.toFile());

                Image newImage = Image.builder()
                        .path("/images/category/" + filename)
                        .build();
                imageRepository.save(newImage);

                existingCategory.setImage(newImage);
            } catch (IOException e) {
                throw new RuntimeException("Error in saving image: " + e.getMessage());
            }
        }

        categoryRepository.save(existingCategory);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void delete(Long id) {
        categoryRepository.softDeleteById(id);
    }

    public void restore(Long id) {
        categoryRepository.restoreById(id);
    }
}
