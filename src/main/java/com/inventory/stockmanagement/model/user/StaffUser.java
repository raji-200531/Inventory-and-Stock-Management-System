package com.inventory.stockmanagement.model.user;

public class StaffUser extends UserAccount {
    public StaffUser() {
        setRole(UserRole.STAFF);
    }

    public StaffUser(String name, String email, String phone, String address, String username, String password) {
        super(name, email, phone, address, username, password, UserRole.STAFF);
    }

    @Override
    public String getAccessLevel() {
        return "STAFF_OPERATION_ACCESS";
    }
}
