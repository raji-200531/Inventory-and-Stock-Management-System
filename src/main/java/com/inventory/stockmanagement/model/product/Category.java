package com.inventory.stockmanagement.model.product;

import com.inventory.stockmanagement.model.base.BaseEntity;

public class Category extends BaseEntity {
    private String name;
    private String description;
    private boolean active = true;

    public Category() {}
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String displayDetails() {
        return "Category: " + name + " - " + description;
    }

    public boolean isValidCategory() {
        return name != null && !name.isBlank();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
