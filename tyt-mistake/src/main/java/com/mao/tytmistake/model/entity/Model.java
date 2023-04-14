package com.mao.tytmistake.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Model {

    YARIS("Yaris"),
    COROLLA("Corolla"),
    AVENSIS("Avensis"),
    AURIS("Auris"),
    HILUX("Hilux");

    private final String name;
}
