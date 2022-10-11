package com.adastra.controllers;

import com.adastra.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public String getUserPublications(Model model, @PathVariable("id") UUID userId) {
        model.addAttribute("publications",userService.getAllPublications(userId));
        model.addAttribute("username",userId);

        return "user-images";
    }
}
