package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.service.DefectLocationService;
import com.mao.tytmistake.service.GetAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This Controller used to create, update, delete and retrieve locaitons
 */
@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    public final DefectLocationService defectLocationService;
    public final GetAllService getAllService;

    /**
     * Retrieve Defect locations
     *
     * @param headers  UserName, Bearer Token
     * @param defectId Defect id
     * @return LocationsResponseList contain defect locations
     */
    @GetMapping("/{defectId}")
    public BaseResponse<List<LocationsResponse>> getAllDefectLocation(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long defectId) {
        List<LocationsResponse> responses = getAllService.getAllLocations(headers, defectId);
        return BaseResponse.isSuccess(responses);
    }

    /**
     * Create new location
     *
     * @param headers               UserName, Bearer Token
     * @param defectLocationRequest Location information to register
     * @return DefectLocationResponse with created defect information
     */
    @PostMapping
    public BaseResponse<DefectLocationResponse> addNewLocations(
            @RequestHeader HttpHeaders headers,
            @RequestBody DefectLocationRequest defectLocationRequest) {
        DefectLocationResponse response = defectLocationService.addNewLocation(headers, defectLocationRequest);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Update exits defect
     *
     * @param headers          UserName, Bearer Token
     * @param locationsRequest Defect information to update
     * @param locationId       Defect id to update
     * @return LocationsResponse with updated defect information
     */
    @PutMapping("/{locationId}")
    public BaseResponse<LocationsResponse> updateLocations(
            @RequestHeader HttpHeaders headers,
            @RequestBody LocationsRequest locationsRequest,
            @PathVariable Long locationId) {
        LocationsResponse response = defectLocationService.updateLocation(headers, locationId, locationsRequest);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Remove exits defect
     *
     * @param headers               UserName, Bearer Token
     * @param locationRemoveRequest Defect information to remove
     * @return Long that removed defect id
     */
    @DeleteMapping()
    public BaseResponse<List<Long>> removeLocations(
            @RequestHeader HttpHeaders headers,
            @RequestBody LocationRemoveRequest locationRemoveRequest) {
        defectLocationService.removeLocation(headers, locationRemoveRequest);
        return BaseResponse.isSuccess(locationRemoveRequest.getLocationIds());
    }

}
