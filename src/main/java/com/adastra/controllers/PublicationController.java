package com.adastra.controllers;

import com.adastra.models.dtos.publication.CreatePublicationDto;
import com.adastra.models.dtos.publication.PublicationDetailsDto;
import com.adastra.models.principal.AppUserDetails;
import com.adastra.services.PublicationService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/images")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/all")
    public String getExplore(Model model) {
        if (!model.containsAttribute("publications"))
            model.addAttribute("publications", publicationService.getAllPublicationItems());
        return "images";
    }

    @GetMapping("/upload")
    public String getAdd(Model model) {
        if (!model.containsAttribute("createPublicationDto"))
            model.addAttribute("createPublicationDto", new CreatePublicationDto());
        return "create";
    }

    @PostMapping("/upload")
    public String create(@Valid CreatePublicationDto createPublicationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AppUserDetails userDetails) {
        if (bindingResult.hasErrors()) {

            return "redirect:/publications/create";
        }
        UUID uuid = publicationService.create(createPublicationDto, userDetails.getId());
        return "redirect:/publications/" + uuid;
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable UUID id, Model model) {
        PublicationDetailsDto publication = publicationService.findById(id);
        model.addAttribute("publication", publication);
        return "details";
    }
}
