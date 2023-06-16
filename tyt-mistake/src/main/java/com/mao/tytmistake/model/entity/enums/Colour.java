package com.mao.tytmistake.model.entity.enums;

import lombok.AllArgsConstructor;

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
