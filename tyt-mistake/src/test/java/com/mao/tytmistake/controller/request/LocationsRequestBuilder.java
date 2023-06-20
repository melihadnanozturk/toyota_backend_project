package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.service.impl.TestDataBuilder;

public class LocationsRequestBuilder extends TestDataBuilder<LocationsRequest> {

    public LocationsRequestBuilder() {
        super(LocationsRequest.class, true);
    }
}
