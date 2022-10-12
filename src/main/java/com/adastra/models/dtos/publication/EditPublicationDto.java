package com.adastra.models.dtos.publication;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class EditPublicationDto {
    private UUID id;
    @NotBlank
    private String title;
    @NotBlank
    private String image;
    private String description;
    public EditPublicationDto(UUID id) {
        this.id = id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
