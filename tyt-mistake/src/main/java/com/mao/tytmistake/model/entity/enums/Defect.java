package com.mao.tytmistake.model.entity.enums;

import lombok.Getter;

/**
 * This Enum holds defects
 */
@Getter
public enum Defect {

    DENT("7000"),
    FADING_PAINT("7001"),
    RUST("7002"),
    SCRATCH("7003");

    private final String code;

    Defect(String code) {
        this.code = code;
    }
}
