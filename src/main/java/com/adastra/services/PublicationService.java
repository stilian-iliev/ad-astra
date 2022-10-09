package com.adastra.services;

import com.adastra.models.Publication;
import com.adastra.models.dtos.publication.CreatePublicationDto;
import com.adastra.repositories.PublicationRepository;
import com.adastra.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
}
