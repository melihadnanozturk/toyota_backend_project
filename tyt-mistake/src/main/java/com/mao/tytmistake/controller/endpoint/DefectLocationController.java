package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.service.DefectLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class DefectLocationController {

    //todo: crud diğer işlemler için kontrol et

    public final DefectLocationService defectLocationService;

    @GetMapping("/{defectId}")
    public BaseResponse<List<LocationsResponse>> getAllDefectLocation(@PathVariable Long defectId) {
        List<LocationsResponse> responses = defectLocationService.findAll(defectId);
        return BaseResponse.isSuccess(responses);
    }

    @PostMapping
    public BaseResponse<DefectLocationResponse> addNewLocations(@RequestBody DefectLocationRequest defectLocationRequest) {
        DefectLocationResponse response = defectLocationService.addNewLocation(defectLocationRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping
    public void updateLocations() {
    }

    @DeleteMapping()
    public BaseResponse<List<Long>> removeLocations(@RequestBody LocationRemoveRequest locationRemoveRequest) {
        defectLocationService.removeLocation(locationRemoveRequest);
        return BaseResponse.isSuccess(locationRemoveRequest.getLocationIds());
    }

}
