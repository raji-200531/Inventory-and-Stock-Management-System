package com.inventory.stockmanagement.model.user;

import com.inventory.stockmanagement.model.person.Person;

public class UserAccount extends Person {
    private String username;
    private String password;
    private UserRole role = UserRole.STAFF;
    private boolean active = true;

    public UserAccount() {
    }

    public UserAccount(String name, String email, String phone, String address, String username, String password, UserRole role) {
        super(name, email, phone, address);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getAccessLevel() {
        return "STANDARD_ACCESS";
    }

    @Override
    public String displayDetails() {
        return username + " | " + role + " | " + getAccessLevel();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
