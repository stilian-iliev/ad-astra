package com.adastra.repositories;

import com.adastra.models.Publication;
import com.adastra.models.dtos.publication.PublicationItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    List<Publication> findByUserId(UUID id);
}
