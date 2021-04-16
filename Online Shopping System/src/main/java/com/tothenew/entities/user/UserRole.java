package com.tothenew.entities.user;

public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    SELLER("ROLE_SELLER"),
    CUSTOMER("ROLE_SELLER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
