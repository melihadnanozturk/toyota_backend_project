package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface DefectLocationService {

    DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest defectLocationRequest);

    LocationsResponse updateLocation(HttpHeaders headers, Long locationId, LocationsRequest request);

    List<Long> removeLocation(HttpHeaders headers, LocationRemoveRequest locationRemoveRequest);
}
