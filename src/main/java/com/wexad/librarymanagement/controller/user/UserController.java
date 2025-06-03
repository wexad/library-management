package com.wexad.librarymanagement.controller.user;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.service.BookService;
import com.wexad.librarymanagement.service.CategoryService;
import com.wexad.librarymanagement.service.ReservationService;
import com.wexad.librarymanagement.service.UserService;
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
    private final ReservationService reservationService;
    private final UserService userService;

    public UserController(BookService bookService, CategoryService categoryService, ReservationService reservationService, UserService userService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
        this.reservationService = reservationService;
        this.userService = userService;
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
    public String reserveBook(@RequestParam Integer bookId,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate pickupDate,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate) {
        reservationService.reserve(bookId, pickupDate, returnDate);
        return "redirect:/profile";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("user_data", userService.getUser());
        model.addAttribute("reservations", reservationService.getUserReservations());
        return "profile";
    }
}
