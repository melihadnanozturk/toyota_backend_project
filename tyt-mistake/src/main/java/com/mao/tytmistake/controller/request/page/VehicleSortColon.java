package com.mao.tytmistake.controller.request.page;

import lombok.Getter;

/**
 * This Enum holds defect sort colons
 */
@Getter
public enum VehicleSortColon implements TytSortCol {

    ID("id"),
    MODEL("model"),
    COLOUR("colour");

    final String colon;

    VehicleSortColon(String colon) {
        this.colon = colon;
    }
}
