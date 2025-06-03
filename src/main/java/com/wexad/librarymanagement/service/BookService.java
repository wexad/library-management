package com.wexad.librarymanagement.service;

import com.wexad.librarymanagement.dto.BookDTO;
import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.entity.Image;
import com.wexad.librarymanagement.mapper.BookMapper;
import com.wexad.librarymanagement.repository.BookRepository;
import com.wexad.librarymanagement.repository.CategoryRepository;
import com.wexad.librarymanagement.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public BookService(BookMapper bookMapper, BookRepository bookRepository, CategoryRepository categoryRepository, ImageRepository imageRepository) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public List<BookDTO> findAll() {
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    public List<BookDTO> findAllDeleted() {
        return bookMapper.toDtoList(bookRepository.findAllDeleted());
    }

    public Image uploadFile(MultipartFile imageFile) throws IOException {
        String uploadDir = "/home/wexad/upload-dir/images/book";
        Files.createDirectories(Paths.get(uploadDir));
        String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir).resolve(filename);
        imageFile.transferTo(filePath.toFile());

        Image newImage = Image.builder()
                .path("/images/book/" + filename)
                .build();
        imageRepository.save(newImage);
        return newImage;
    }

    public void save(String title, String author, String description, int totalCopies, Long categoryId, MultipartFile file) {
        try {
            Image image = uploadFile(file);
            Book book = Book.builder()
                    .title(title)
                    .author(author)
                    .description(description)
                    .totalCopies(totalCopies)
                    .availableCopies(totalCopies)
                    .category(categoryRepository.findById(categoryId).orElseThrow())
                    .image(image)
                    .build();
            bookRepository.save(book);
        } catch (IOException e) {
            throw new RuntimeException("Error in saving image: " + e.getMessage());
        }
    }

    public BookDTO findById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found")));
    }

    public void update(Long id, String title, String author, String description, int totalCopies, Long categoryId, MultipartFile imageFile) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        existingBook.setTitle(title);
        existingBook.setAuthor(author);
        existingBook.setDescription(description);
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
        existingBook.setCategory(category);
        Image newImage;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                newImage = uploadFile(imageFile);

                existingBook.setImage(newImage);
            } catch (IOException e) {
                throw new RuntimeException("Error in saving image: " + e.getMessage());
            }
        }
        bookRepository.save(existingBook);
    }

    public void delete(Long id) {
        bookRepository.softDeleteById(id);
    }

    public void restore(Long id) {
        bookRepository.restoreById(id);
    }

    public List<BookDTO> searchBooks(String keyword, Integer categoryId) {
        if (keyword != null && keyword.trim().isEmpty()) {
            keyword = null;
        }
        if (categoryId != null && categoryId == 0) {
            categoryId = null;
        }
        if ((keyword == null || keyword.isBlank()) && categoryId == null) {
            return bookMapper.toDtoList(bookRepository.findAll());
        }
        return bookMapper.toDtoList(bookRepository.searchByKeywordAndCategory(keyword, categoryId));
    }

    public void isAvailable(Integer bookId, LocalDate pickupDate, LocalDate returnDate) {
        if (!bookRepository.isAvailable(bookId, pickupDate, returnDate)) {
            throw new RuntimeException("Book is not available in this period");
        }
    }
}

