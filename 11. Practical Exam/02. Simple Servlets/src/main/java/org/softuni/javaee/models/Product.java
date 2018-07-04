package org.softuni.javaee.models;

public class Product {

    private String name;
    private String description;
    private ProductType type;

    public Product() {
    }

    public Product(final String name, final String description, final ProductType type) {
        this.name = name;
        this.description = description;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ProductType getType() {
        return this.type;
    }

    public void setType(final ProductType type) {
        this.type = type;
    }
}
