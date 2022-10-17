package com.adastra.repositories.specifications;

import com.adastra.models.Publication;
import com.adastra.models.dtos.publication.SearchPublicationDto;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PublicationSpecification implements Specification<Publication> {
    private final SearchPublicationDto searchPublicationDto;

    public PublicationSpecification(SearchPublicationDto searchPublicationDto) {
        this.searchPublicationDto = searchPublicationDto;
    }

    @Override
    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate p = criteriaBuilder.conjunction();
        if (searchPublicationDto.getQuery() != null && !searchPublicationDto.getQuery().isEmpty() && searchPublicationDto.getSearchBy() != null) {
            switch (searchPublicationDto.getSearchBy()) {
                case TITLE -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.like(root.get("title"), "%" + searchPublicationDto.getQuery() + "%"))
                );
                case USER -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.like(root.join("user").get("username"), "%" + searchPublicationDto.getQuery() + "%"))
                );
                case DESCRIPTION -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.like(root.get("description"), "%" + searchPublicationDto.getQuery() + "%"))
                );
            }

        }
        if (searchPublicationDto.getSortBy() != null) {
            switch (searchPublicationDto.getSortBy()) {
                case NEWEST -> query.orderBy(criteriaBuilder.desc(root.get("publicationTime")));
                case OLDEST -> query.orderBy(criteriaBuilder.asc(root.get("publicationTime")));
                case AZ ->
                        query.orderBy(criteriaBuilder.asc(root.get(searchPublicationDto.getSearchBy().getName().toLowerCase())));
                case ZA ->
                        query.orderBy(criteriaBuilder.desc(root.get(searchPublicationDto.getSearchBy().getName().toLowerCase())));
            }
        }
        return p;
    }
}
