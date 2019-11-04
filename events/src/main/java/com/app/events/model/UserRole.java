package com.app.events.model;

public enum UserRole {
	ADMIN ("ADMIN"), REGULAR("REGULAR");

    private final String name;       

    private UserRole(String s) {
        name = s;
    }

    public String toString() {
       return this.name;
    }
}
