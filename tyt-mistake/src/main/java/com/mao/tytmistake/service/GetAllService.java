package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface GetAllService {

    Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request);

    Page<PageVehicleDefectResponse> getAllDefect(HttpHeaders headers, PageDefectRequest request);

    List<LocationsResponse> getAllLocations(HttpHeaders headers, Long defectId);
}
