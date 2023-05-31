package com.mao.tytmistake.model.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Colour {

    BLUE("Blue"),
    RED("Red"),
    GREY("Grey"),
    BLACK("Black");

    private final String colour;

    @Override
    public String toString() {
        return colour;
    }
}