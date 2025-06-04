package com.wexad.librarymanagement.controller;

import com.wexad.librarymanagement.dto.UserDTO;
import com.wexad.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginAndRegisterController {

    private final UserService userService;

    public LoginAndRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("email") String email,
                               @ModelAttribute("name") String name,
                               @ModelAttribute("password") String password,
                               Model model) {
        userService.registerUser(email, name, password);
        return "redirect:/login?registered";
    }
}
