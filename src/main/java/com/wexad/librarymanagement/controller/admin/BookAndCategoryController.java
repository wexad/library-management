package com.wexad.librarymanagement.controller.admin;

import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class BookAndCategoryController {
    private final CategoryService categoryService;

    public BookAndCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/category")
    public String category(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        return "admin/category";
    }

    @GetMapping("/category/create")
    public String createCategory() {
        return "admin/category-create";
    }

    @PostMapping("/category/create")
    public String saveCategory(@RequestParam(value = "name") String name,
                               @RequestParam(value = "file") MultipartFile file) {
        categoryService.save(name, file);
        return "redirect:/admin/category";
    }
}

