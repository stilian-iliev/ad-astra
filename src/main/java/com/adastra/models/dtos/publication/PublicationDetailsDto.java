package com.adastra.models.dtos.publication;

import com.adastra.models.Publication;

import java.time.LocalDateTime;
import java.util.UUID;

public class PublicationDetailsDto {
    private UUID id;
    private String title;
    private String description;
    private String image;
    private String user;
    private UUID userId;
    private LocalDateTime date;

    public PublicationDetailsDto(Publication publication) {
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.description = publication.getDescription();
        this.image = publication.getImage();
        this.user = publication.getUser().getUsername();
        this.userId = publication.getUser().getId();
        this.date = publication.getPublicationTime();
    }

    public UUID getId() {
        return id;
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

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
