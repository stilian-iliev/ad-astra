package com.adastra.repositories.specifications;

import com.adastra.models.Publication;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class PublicationSpecification implements Specification<Publication> {
    @Override
    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
