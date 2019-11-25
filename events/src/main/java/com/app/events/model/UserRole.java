package com.app.events.model;

public enum UserRole {
	ROLE_ADMIN ("ROLE_ADMIN"), ROLE_REGULAR("ROLE_REGULAR");

    private final String name;       

    private UserRole(String s) {
        name = s;
    }

    @Override
    public String toString() {
       return this.name;
    }
}
