package com.mao.tytmistake.controller.request.page;

import lombok.Getter;

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
