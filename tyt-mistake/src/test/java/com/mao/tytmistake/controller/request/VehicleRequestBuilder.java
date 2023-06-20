package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.TestDataBuilder;

public class VehicleRequestBuilder extends TestDataBuilder<VehicleRequest> {

    public VehicleRequestBuilder() {
        super(VehicleRequest.class, true);
    }

    public VehicleRequestBuilder withChassisNumber(String chassisNumber) {
        data.setChassisNumber(chassisNumber);
        return this;
    }
}
