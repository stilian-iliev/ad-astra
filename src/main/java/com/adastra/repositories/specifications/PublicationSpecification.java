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
        if (searchPublicationDto.getQuery() != null && !searchPublicationDto.getQuery().isEmpty()) {
            switch (searchPublicationDto.getSearchBy()) {
                case EVERYTHING -> {
                    //todo
                }
                case TITLE -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.equal(root.get("title"), searchPublicationDto.getQuery()))
                );
                case USER -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.equal(root.join("user").get("username"), searchPublicationDto.getQuery()))
                );
                case DESCRIPTION -> p.getExpressions().add(
                        criteriaBuilder.and(criteriaBuilder.equal(root.get("description"), searchPublicationDto.getQuery()))
                );
            }

        }

        switch (searchPublicationDto.getSortBy()){
            case NEWEST -> query.orderBy();
            case OLDEST -> query.orderBy();
            case AZ -> query.orderBy();
            case ZA -> query.orderBy();
        }
        return p;
    }
}
