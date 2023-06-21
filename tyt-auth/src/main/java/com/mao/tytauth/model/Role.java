package com.mao.tytauth.model;

import lombok.Getter;

/**
 * This Enum holds user roles
 * This class uses Lombok annotations @Getter to automatically generate getters
 */
@Getter
public enum Role {
    ADMIN("ADMIN"),
    OPERATOR("OPERATOR"),
    TEAM_LEAD("TEAM_LEAD");

    private String name;

    Role(String name) {
        this.name = name;
    }
}
