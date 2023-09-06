package com.adastra.models.dtos.publication;

import com.adastra.models.enumerations.publication.SearchPublicationSort;
import com.adastra.models.enumerations.publication.SearchPublicationCriteria;

import javax.validation.constraints.NotEmpty;

public class SearchPublicationDto {
    private String query;
    private SearchPublicationCriteria searchBy;
    private SearchPublicationSort sortBy;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public SearchPublicationCriteria getSearchBy() {
        return searchBy;
    }

    public void setSearchBy(SearchPublicationCriteria searchBy) {
        this.searchBy = searchBy;
    }

    public SearchPublicationSort getSortBy() {
        return sortBy;
    }

    public void setSortBy(SearchPublicationSort sortBy) {
        this.sortBy = sortBy;
    }
}
