package com.wexad.librarymanagement.controller.user;

import com.wexad.librarymanagement.service.BookService;
import com.wexad.librarymanagement.service.CategoryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class UserController {

    private final BookService bookService;
    private final CategoryService categoryService;
//    private final ReservationService reservationService;

    public UserController(BookService bookService, CategoryService categoryService/*, ReservationService reservationService*/) {
        this.bookService = bookService;
        this.categoryService = categoryService;
//        this.reservationService = reservationService;
    }

    @GetMapping("/books")
    public String viewBooks(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false) Integer categoryId,
                            Model model) {
        model.addAttribute("books", bookService.searchBooks(keyword, categoryId));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);

        return "index";
    }



    @PostMapping("/reserve")
    public String reserveBook(@RequestParam Long bookId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
//        reservationService.reserveBook(bookId, pickupDate, returnDate);
        return "redirect:/books";
    }
}
