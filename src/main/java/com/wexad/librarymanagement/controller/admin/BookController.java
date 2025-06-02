package com.wexad.librarymanagement.controller.admin;

import com.wexad.librarymanagement.entity.Book;
import com.wexad.librarymanagement.service.BookService;
import com.wexad.librarymanagement.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/book")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String listBooks(Model model) {
        model.addAttribute("booksActive", bookService.findAll());
        model.addAttribute("booksInactive", bookService.findAllDeleted());
        return "admin/book";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/book-create";
    }

    @PostMapping("/create")
    public String save(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String description,
                           @RequestParam int totalCopies,
                           @RequestParam Long categoryId,
                           @RequestParam MultipartFile imageFile) {
        bookService.save(title, author, description, totalCopies, categoryId, imageFile);
        return "redirect:/admin/book";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "admin/book-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String description,
                             @RequestParam int totalCopies,
                             @RequestParam Long categoryId,
                             @RequestParam MultipartFile imageFile) {
        bookService.update(id, title, author, description, totalCopies, categoryId, imageFile);
        return "redirect:/admin/book";
    }

    @PostMapping("/{id}/delete")
    public String softDelete(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/admin/book";
    }

    @PostMapping("/{id}/restore")
    public String restoreCategory(@PathVariable Long id) {
        bookService.restore(id);
        return "redirect:/admin/book";
    }
}

