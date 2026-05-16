package com.inventory.stockmanagement.model.person;

import com.inventory.stockmanagement.model.base.BaseEntity;

public abstract class Person extends BaseEntity {
    private String name;
    private String email;
    private String phone;
    private String address;

    protected Person() {}

    protected Person(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public String getContactDetails() {
        return name + " | " + email + " | " + phone;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
