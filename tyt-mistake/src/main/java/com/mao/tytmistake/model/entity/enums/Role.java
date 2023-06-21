package com.mao.tytmistake.model.entity.enums;

/**
 * This Enum holds user roles
 */
public enum Role {
    ADMIN("ADMIN"),
    OPERATOR("OPERATOR"),
    TEAM_LEAD("TEAM_LEAD");

    private String name;

    Role(String name) {
        this.name = name;
    }

}
