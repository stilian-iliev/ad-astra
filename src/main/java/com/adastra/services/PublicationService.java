package com.adastra.services;

import com.adastra.models.Publication;
import com.adastra.models.dtos.publication.CreatePublicationDto;
import com.adastra.models.dtos.publication.EditPublicationDto;
import com.adastra.models.dtos.publication.PublicationDetailsDto;
import com.adastra.models.dtos.publication.PublicationItemDto;
import com.adastra.models.principal.AppUserDetails;
import com.adastra.repositories.PublicationRepository;
import com.adastra.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public PublicationService(PublicationRepository publicationRepository, UserRepository userRepository, ModelMapper mapper) {
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public boolean isOwner(AppUserDetails userDetails, UUID publicationId) {
        if (userDetails == null) return false;
        UUID ownerId = publicationRepository.findById(publicationId).orElseThrow().getUser().getId();
        return ownerId.equals(userDetails.getId());
    }

    public UUID create(CreatePublicationDto createPublicationDto, UUID userId) {
        Publication publication = mapper.map(createPublicationDto, Publication.class);
        publication.setUser(userRepository.findById(userId).orElseThrow());
        return publicationRepository.save(publication).getId();
    }

    public PublicationDetailsDto findById(UUID id) {
        return publicationRepository.findById(id).map(PublicationDetailsDto::new).orElseThrow();
    }

    public List<PublicationItemDto> getAllPublicationItems() {
        return publicationRepository.findAll().stream().map(PublicationItemDto::new).collect(Collectors.toList());
    }

    public Publication getById(UUID id) {
        return publicationRepository.findById(id).orElseThrow();
    }

    public void deletePublication(UUID id) {
        publicationRepository.deleteById(id);
    }

    public void editPublication(UUID id, EditPublicationDto editPublicationDto) {
        Publication publication = publicationRepository.findById(id).orElseThrow();
        publication.setTitle(editPublicationDto.getTitle());
        publication.setImage(editPublicationDto.getImage());
        publication.setDescription(editPublicationDto.getDescription());
        publicationRepository.save(publication);
    }
}
