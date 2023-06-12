package com.mao.tytmistake.model.entity.enums;

public enum Role {
    ADMIN("ADMIN"),
    OPERATOR("OPERATOR"),
    TEAM_LEAD("TEAM_LEAD");

    private String name;

    Role(String name) {
        this.name = name;
    }

}
