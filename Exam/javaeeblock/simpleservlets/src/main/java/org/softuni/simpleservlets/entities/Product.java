package org.softuni.simpleservlets.entities;

public class Product {
    private String name;

    private String description;

    private Type productType;

    public Product() {
    }

    public Product(String name, String description, Type productType) {
        this.name = name;
        this.description = description;
        this.productType = productType;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getProductType() {
        return this.productType;
    }

    public void setProductType(Type productType) {
        this.productType = productType;
    }
}
