package com.mao.tytmistake.model.entity.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * This Enum holds vehicle models
 */
@Getter
@ToString
public enum Model {

    YARIS("Yaris"),
    COROLLA("Corolla"),
    AVENSIS("Avensis"),
    AURIS("Auris"),
    HILUX("Hilux");

    private final String name;

    Model(String name) {
        this.name = name;
    }
}
