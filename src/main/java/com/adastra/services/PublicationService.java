package com.adastra.services;

import com.adastra.models.Publication;
import com.adastra.models.dtos.publication.CreatePublicationDto;
import com.adastra.models.dtos.publication.PublicationDetailsDto;
import com.adastra.models.dtos.publication.PublicationItemDto;
import com.adastra.repositories.PublicationRepository;
import com.adastra.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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
}
