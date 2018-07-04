package org.softuni.javaee.model;

public enum ProductType {
    FOOD("Food"),
    DOMESTIC("Domestic"),
    HEALTH("Health"),
    COSMETIC("Cosmetic"),
    OTHER("Other");

    private final String name;

    ProductType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
