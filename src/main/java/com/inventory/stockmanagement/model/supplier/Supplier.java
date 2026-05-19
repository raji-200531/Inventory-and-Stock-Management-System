package com.inventory.stockmanagement.model.supplier;

import com.inventory.stockmanagement.model.person.Person;

public class Supplier extends Person {
    private String companyName;
    private boolean active = true;

    public Supplier() {}
    public Supplier(String name, String companyName, String email, String phone, String address) {
        super(name, email, phone, address);
        this.companyName = companyName;
    }

    @Override
    public String getContactDetails() {
        return companyName + " | " + getName() + " | " + getEmail() + " | " + getPhone();
    }

    @Override
    public String displayDetails() {
        return "Supplier: " + companyName + " (" + getName() + ")";
    }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
