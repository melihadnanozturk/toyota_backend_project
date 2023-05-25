package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.PageDefectLocationResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import org.springframework.data.domain.Page;

public interface GetAllService {

    Page<PageVehicleResponse> getAllVehicle(PageVehicleRequest request);

    Page<PageVehicleDefectResponse> getAllVehicleDefect(PageVehicleDefectRequest request);

    //todo: will added
    PageDefectLocationResponse findAll();
}
