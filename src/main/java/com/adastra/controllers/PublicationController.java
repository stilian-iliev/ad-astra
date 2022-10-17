package com.adastra.controllers;

import com.adastra.models.dtos.publication.*;
import com.adastra.models.principal.AppUserDetails;
import com.adastra.services.PublicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequestMapping("/publications")
public class PublicationController {
    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping("/all")
    public String getExplore(Model model , @Valid SearchPublicationDto searchPublicationDto,
            @PageableDefault(
            page = 0,
            size = 20)Pageable pageable){
        if (!model.containsAttribute("searchPublicationDto"))
            model.addAttribute("searchPublicationDto", new SearchPublicationDto());
        if (!model.containsAttribute("publications"))
            model.addAttribute("publications", publicationService.getAllPublicationItems(pageable, searchPublicationDto));
        Page<PublicationItemDto> allPublicationItems = publicationService.getAllPublicationItems(pageable, searchPublicationDto);
        return "publications";
    }

    @GetMapping("/upload")
    public String getAdd(Model model) {
        if (!model.containsAttribute("createPublicationDto"))
            model.addAttribute("createPublicationDto", new CreatePublicationDto());
        return "upload";
    }

    @PostMapping("/upload")
    public String create(@Valid CreatePublicationDto createPublicationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal AppUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("createPublicationDto", createPublicationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.createPublicationDto", bindingResult);
            return "redirect:/publications/upload";
        }
        UUID uuid = publicationService.create(createPublicationDto, userDetails.getId());
        return "redirect:/publications/" + uuid;
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable("id") UUID id, Model model, @AuthenticationPrincipal AppUserDetails userDetails) {
        PublicationDetailsDto publication = publicationService.findById(id);
        model.addAttribute("publication", publication);
        model.addAttribute("isOwner", userDetails != null && userDetails.getId().equals(publication.getUserId()));
        return "details";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @publicationService.isOwner(#userDetails, #id)")
    @GetMapping("/{id}/edit")
    public String getEdit(@PathVariable("id") UUID id, Model model) {
        if (!model.containsAttribute("editPublicationDto"))
            model.addAttribute("editPublicationDto", publicationService.getById(id));
        return "edit";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @publicationService.isOwner(#userDetails, #id)")
    @PutMapping("/{id}")
    public String edit(@Valid EditPublicationDto editPublicationDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable("id") UUID id) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editPublicationDto", editPublicationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editPublicationDto", bindingResult);
            return "redirect:/publications/"+ id + "/edit";
        }
        publicationService.editPublication(id, editPublicationDto);
        return "redirect:/publications/" + id;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @publicationService.isOwner(#userDetails, #id)")
    @DeleteMapping("/{id}")
    public String deletePublication(@PathVariable("id") UUID id, @AuthenticationPrincipal AppUserDetails userDetails) {
        publicationService.deletePublication(id);
        return "redirect:/publications/all";
    }

}
