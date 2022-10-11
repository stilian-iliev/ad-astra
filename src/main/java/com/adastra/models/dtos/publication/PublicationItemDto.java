package com.adastra.models.dtos.publication;

import com.adastra.models.Publication;

import java.util.UUID;

public class PublicationItemDto {
    private UUID id;
    private String title;
    private String image;

    public PublicationItemDto(Publication publication) {
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.image = publication.getImage();
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
}
