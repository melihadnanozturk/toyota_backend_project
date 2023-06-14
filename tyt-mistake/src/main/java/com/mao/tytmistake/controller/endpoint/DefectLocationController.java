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

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class DefectLocationController {

    public final DefectLocationService defectLocationService;
    public final GetAllService getAllService;

    @GetMapping("/{defectId}")
    public BaseResponse<List<LocationsResponse>> getAllDefectLocation(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long defectId) {
        List<LocationsResponse> responses = getAllService.getAllLocations(headers, defectId);
        return BaseResponse.isSuccess(responses);
    }

    @PostMapping
    public BaseResponse<DefectLocationResponse> addNewLocations(@RequestBody DefectLocationRequest defectLocationRequest) {
        DefectLocationResponse response = defectLocationService.addNewLocation(defectLocationRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{locationId}")
    public BaseResponse<LocationsResponse> updateLocations(@RequestBody LocationsRequest request, @PathVariable Long locationId) {
        LocationsResponse response = defectLocationService.updateLocation(locationId, request);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping()
    public BaseResponse<List<Long>> removeLocations(@RequestBody LocationRemoveRequest locationRemoveRequest) {
        defectLocationService.removeLocation(locationRemoveRequest);
        return BaseResponse.isSuccess(locationRemoveRequest.getLocationIds());
    }

}
