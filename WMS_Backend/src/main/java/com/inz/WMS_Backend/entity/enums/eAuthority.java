package com.inz.WMS_Backend.entity.enums;

import lombok.Getter;

@Getter
public enum eAuthority {
    USER("USER"),
    ADMIN("ADMIN");

    private final String role;

    eAuthority(String role) {
        this.role = role;
    }
}
