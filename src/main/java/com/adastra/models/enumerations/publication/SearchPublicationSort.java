package com.adastra.models.enumerations.publication;

public enum SearchPublicationSort {
    NEWEST("Newest"), OLDEST("Oldest"), AZ("A-Z"), ZA("Z-A");

    private final String name;

    SearchPublicationSort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
