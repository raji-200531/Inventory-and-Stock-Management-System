package com.inventory.stockmanagement.model.user;

public class AdminUser extends UserAccount {
    public AdminUser() {
        setRole(UserRole.ADMIN);
    }

    public AdminUser(String name, String email, String phone, String address, String username, String password) {
        super(name, email, phone, address, username, password, UserRole.ADMIN);
    }

    @Override
    public String getAccessLevel() {
        return "FULL_ADMIN_ACCESS";
    }
}
