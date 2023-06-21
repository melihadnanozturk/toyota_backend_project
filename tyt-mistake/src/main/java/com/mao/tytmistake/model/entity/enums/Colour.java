package com.mao.tytmistake.model.entity.enums;

import lombok.AllArgsConstructor;

/**
 * This Enum holds vehicle colours
 */
@AllArgsConstructor
public enum Colour {

    BLUE("Blue"),
    RED("Red"),
    GREY("Grey"),
    BLACK("Black");

    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
