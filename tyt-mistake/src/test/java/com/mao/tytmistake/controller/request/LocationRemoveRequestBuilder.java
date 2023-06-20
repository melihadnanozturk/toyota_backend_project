package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.service.impl.TestDataBuilder;

import java.util.List;

public class LocationRemoveRequestBuilder extends TestDataBuilder<LocationRemoveRequest> {

    public LocationRemoveRequestBuilder() {
        super(LocationRemoveRequest.class, true);
    }

    public LocationRemoveRequestBuilder withIds(List<Long> ids) {
        data.setLocationIds(ids);
        return this;
    }
}
