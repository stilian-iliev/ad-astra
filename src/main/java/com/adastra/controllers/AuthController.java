package com.adastra.controllers;

import com.adastra.models.dtos.user.RegisterDto;
import com.adastra.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {
    private final UserService userService;

    @Value("${spring.captcha.siteKey}")
    private String captchaSiteKey;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("captchaSiteKey", captchaSiteKey);
        return "login";
    }


    @GetMapping("/register")
    public String getRegister(Model model) {
        if (!model.containsAttribute("registerDto"))
            model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto registerDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDto", registerDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDto", bindingResult);
            return "redirect:/register";
        }
        userService.register(registerDto);

        return "redirect:/";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(@ModelAttribute("username") String userName,
                                @RequestAttribute(WebAttributes.AUTHENTICATION_EXCEPTION) AuthenticationException ex,
                                RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("username", userName);
        redirectAttributes.addFlashAttribute("hasError", true);
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/login";
    }
}
