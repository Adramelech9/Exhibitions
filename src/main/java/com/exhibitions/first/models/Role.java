package com.exhibitions.first.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, USHER;

    @Override
    public String getAuthority() {
        return name();
    }
}
