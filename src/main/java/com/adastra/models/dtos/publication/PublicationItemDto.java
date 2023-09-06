package com.adastra.models.dtos.publication;

import com.adastra.models.Publication;

import java.util.UUID;

public class PublicationItemDto {
    private UUID id;
    private String title;
    private String image;

    public PublicationItemDto(Publication publication) {
        this.id = publication.getId();
        if (publication.getTitle().length() > 15) {
            this.title = publication.getTitle().substring(0, 15) + "...";
        } else {
            this.title = publication.getTitle();
        }
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
