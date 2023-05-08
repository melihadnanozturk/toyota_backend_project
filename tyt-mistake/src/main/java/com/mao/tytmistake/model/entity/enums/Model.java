package com.mao.tytmistake.model.entity.enums;

import lombok.Getter;
import lombok.ToString;

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
