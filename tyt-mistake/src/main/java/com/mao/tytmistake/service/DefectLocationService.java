package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;

import java.util.List;

public interface DefectLocationService {

    List<LocationsResponse> findAll(Long defectId);

    DefectLocationResponse addNewLocation(DefectLocationRequest defectLocationRequest);

    LocationsResponse updateLocation(Long locationId, LocationsRequest request);

    List<Long> removeLocation(LocationRemoveRequest locationRemoveRequest);
}
