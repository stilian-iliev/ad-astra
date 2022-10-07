package com.adastra.controllers;

import com.adastra.models.dtos.user.RegisterDto;
import com.adastra.repositories.UserRoleRepository;
import com.adastra.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;


    public AuthController(UserService userService) {
        this.userService = userService;

    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }



    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {

            return "redirect:/register";
        }
        userService.register(registerDto);

        return "redirect:/";
    }
}
