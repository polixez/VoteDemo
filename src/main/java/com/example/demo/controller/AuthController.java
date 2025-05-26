package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Отображение страницы регистрации
    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    // Обработка формы регистрации
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        try {
            userService.registerUser(username, password);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
        return "redirect:/login";
    }

    // Отображение страницы входа
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверное имя пользователя или пароль!");
        }
        if (logout != null) {
            model.addAttribute("message", "Вы успешно вышли из системы.");
        }
        return "login";
    }
}
