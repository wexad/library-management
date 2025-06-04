package com.wexad.librarymanagement.controller.admin;

import com.wexad.librarymanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class UserManageController {
    private final UserService userService;

    public UserManageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAll());
        return "admin/user";
    }

    @PostMapping("/user/create")
    public String createAdmin(@RequestParam String name,
                              @RequestParam String email,
                              @RequestParam String password,
                              @RequestParam String role) {
        userService.createAdmin(name, email, password, role);
        return "redirect:/admin/users";
    }
}
