package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.TestDataBuilder;

public class UpdateVehicleDefectRequestBuilder extends TestDataBuilder<UpdateDefectRequest> {

    public UpdateVehicleDefectRequestBuilder() {
        super(UpdateDefectRequest.class, true);
    }

    public UpdateVehicleDefectRequestBuilder withImage(String image) {
        data.setDefectImage(image);
        return this;
    }
}
