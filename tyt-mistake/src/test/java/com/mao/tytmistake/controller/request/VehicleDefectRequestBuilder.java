package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.service.impl.TestDataBuilder;

public class VehicleDefectRequestBuilder extends TestDataBuilder<VehicleDefectRequest> {

    public VehicleDefectRequestBuilder() {
        super(VehicleDefectRequest.class, true);
    }

    public VehicleDefectRequestBuilder witVehicleId(Long vehicleId) {
        data.setVehicleId(vehicleId);
        return this;
    }
}
