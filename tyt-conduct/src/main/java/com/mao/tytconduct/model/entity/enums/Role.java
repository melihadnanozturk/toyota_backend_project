package com.mao.tytconduct.model.entity.enums;

public enum Role {
    ADMIN("admin"),
    OPERATOR("operator"),
    TEAM_LEAD("team_lead");

    private String name;

    Role(String name) {
        this.name = name;
    }

}
