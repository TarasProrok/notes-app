package com.ratatui.notes.user;

public enum UserRoles {
    ROLE_ADMIN ("Admin"),
    ROLE_USER ("User");

    private String frendlyName;

    UserRoles(String friendlyName) {
        this.frendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return frendlyName;
    }
}