package com.wexad.librarymanagement.controller.admin;

import com.wexad.librarymanagement.entity.Category;
import com.wexad.librarymanagement.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String category(Model model) {
        model.addAttribute("categoriesInactive", categoryService.findAllDeleted());
        model.addAttribute("categoriesActive", categoryService.findAll());
        return "admin/category";
    }

    @GetMapping("/create")
    public String createCategory() {
        return "admin/category-create";
    }

    @PostMapping("/create")
    public String saveCategory(@RequestParam(value = "name") String name,
                               @RequestParam(value = "file") MultipartFile file) {
        categoryService.save(name, file);
        return "redirect:/admin/category";
    }

    @GetMapping("/edit/{id}")
    public String editCategoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("category", categoryService.findById(id));
        return "admin/category-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCategory(@PathVariable(name = "id") Long id,
                                 @ModelAttribute Category category,
                                 @RequestParam("imageFile") MultipartFile imageFile) {
        categoryService.updateCategory(id, category, imageFile);
        return "redirect:/admin/category";
    }

    @PostMapping("/{id}/delete")
    public String softDelete(@PathVariable Long id) {
        categoryService.delete(id);
        return "redirect:/admin/category";
    }

    @PostMapping("/{id}/restore")
    public String restoreCategory(@PathVariable Long id) {
        categoryService.restore(id);
        return "redirect:/admin/category";
    }
}

