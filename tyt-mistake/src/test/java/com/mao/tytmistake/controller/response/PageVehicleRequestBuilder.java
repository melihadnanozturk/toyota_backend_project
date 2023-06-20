package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.request.page.VehicleSortColon;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class PageVehicleRequestBuilder extends TestDataBuilder<PageVehicleRequest> {

    public PageVehicleRequestBuilder() {
        super(PageVehicleRequest.class, true);
    }

    public PageVehicleRequestBuilder withSortCol(VehicleSortColon sortCol) {
        data.setSortCol(sortCol);
        return this;
    }
}
