package com.adastra.models.dtos.publication;

import javax.validation.constraints.NotBlank;

public class CreatePublicationDto {
    @NotBlank
    private String title;
    @NotBlank
    private String image;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
