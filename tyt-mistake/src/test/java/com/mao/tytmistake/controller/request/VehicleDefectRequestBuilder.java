package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.TestDataBuilder;

public class VehicleDefectRequestBuilder extends TestDataBuilder<DefectRequest> {

    public VehicleDefectRequestBuilder() {
        super(DefectRequest.class, true);
    }

    public VehicleDefectRequestBuilder witVehicleId(Long vehicleId) {
        data.setVehicleId(vehicleId);
        return this;
    }
}
