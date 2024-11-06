package com.inz.WMS_Backend.entity.enums;

import lombok.Getter;

@Getter
public enum eAuthority {
    NEW_USER("NEW USER"),
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String role;

    eAuthority(String role) {
        this.role = role;
    }
}
