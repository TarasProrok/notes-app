package com.ratatui.notes.user;

public enum UserRoles {
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private String friendlyName;

    UserRoles(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
