package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.model.exception.NotFoundException;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Service interface for location functionality.
 */
public interface DefectLocationService {

    /**
     * This method create new location when client is valid.
     *
     * @param headers               Username, Password
     * @param defectLocationRequest Contains new location information
     */
    DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest defectLocationRequest);

    /**
     * This method update exists location when client is valid.
     *
     * @param headers          Username, Password
     * @param locationsRequest Contains location new information
     * @throws NotFoundException if location that has id not exists
     */
    LocationsResponse updateLocation(HttpHeaders headers, Long locationId, LocationsRequest locationsRequest);

    /**
     * This method remove exists location when client is valid.
     *
     * @param headers               Username, Password
     * @param locationRemoveRequest Contains id of the location to be deleted
     * @throws NotFoundException if location that has id not exists
     */
    List<Long> removeLocation(HttpHeaders headers, LocationRemoveRequest locationRemoveRequest);
}
