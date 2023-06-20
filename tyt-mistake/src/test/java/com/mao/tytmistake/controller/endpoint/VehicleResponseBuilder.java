package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class VehicleResponseBuilder extends TestDataBuilder<VehicleResponse> {

    public VehicleResponseBuilder() {
        super(VehicleResponse.class, true);
    }
}
