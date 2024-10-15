package com.inz.WMS_Backend.entity.enums;

import lombok.Getter;

@Getter
public enum eAuthority {
    USER("USER"),
    ADMIN("ADMIN"),
    MANAGER("MANAGER");

    private final String role;

    eAuthority(String role) {
        this.role = role;
    }
}
