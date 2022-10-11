package com.adastra.models.dtos.publication;

import java.util.UUID;

public class EditPublicationDto {
    private UUID id;
    private String title;
    private String image;
    private String description;
    public EditPublicationDto(UUID id) {
        this.id = id;
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
