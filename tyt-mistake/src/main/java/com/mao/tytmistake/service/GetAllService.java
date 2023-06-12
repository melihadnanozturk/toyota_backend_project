package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.PageDefectLocationResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

public interface GetAllService {

    Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request);

    Page<PageVehicleDefectResponse> getAllVehicleDefect(PageVehicleDefectRequest request);

    //todo: will added
    PageDefectLocationResponse findAll();
}
