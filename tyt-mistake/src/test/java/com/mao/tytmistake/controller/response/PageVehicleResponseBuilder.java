package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class PageVehicleResponseBuilder extends TestDataBuilder<PageVehicleResponse> {

    public PageVehicleResponseBuilder() {
        super(PageVehicleResponse.class, true);
    }
}
