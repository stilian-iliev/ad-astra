package com.adastra.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/publications")
public class PublicationController {
    @GetMapping("/create")
    public String getAdd() {
        return "create";
    }
}
