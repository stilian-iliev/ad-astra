package com.adastra.models.dtos.publication;

import com.adastra.models.Publication;

import java.time.LocalDateTime;

public class PublicationDetailsDto {
    private String title;
    private String description;
    private String image;
    private String user;

    private LocalDateTime date;

    public PublicationDetailsDto(Publication publication) {
        this.title = publication.getTitle();
        this.description = publication.getDescription();
        this.image = publication.getImage();
        this.user = publication.getUser().getUsername();
        this.date = publication.getPublicationTime();
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
