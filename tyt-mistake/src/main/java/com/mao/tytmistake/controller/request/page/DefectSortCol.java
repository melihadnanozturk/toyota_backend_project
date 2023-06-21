package com.mao.tytmistake.controller.request.page;

import lombok.Getter;

/**
 * This Enum holds defect sort colons
 */
@Getter
public enum DefectSortCol implements TytSortCol {

    ID("id"),
    DEFECT("defect"),
    VEHICLE_ID("vehicleId");

    final String colon;

    DefectSortCol(String colon) {
        this.colon = colon;
    }
}
