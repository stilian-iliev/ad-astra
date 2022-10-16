package com.adastra.models.enumerations.publication;

public enum SearchPublicationCriteria {
    EVERYTHING("Everything"), TITLE("Title"), DESCRIPTION("Description"), USER("User");

    private final String name;

    SearchPublicationCriteria(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
