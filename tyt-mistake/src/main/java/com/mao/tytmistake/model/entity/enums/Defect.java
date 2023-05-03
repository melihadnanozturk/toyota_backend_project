package com.mao.tytmistake.model.entity.enums;

import lombok.Getter;

@Getter
public enum Defect {

    DENT("1000"),
    FADING_PAINT("1001"),
    RUST("1002"),
    SCRATCH("1003");

    private final String code;

    Defect(String code) {
        this.code = code;
    }
}
