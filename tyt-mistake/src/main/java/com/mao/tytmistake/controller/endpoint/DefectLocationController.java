package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.PageDefectLocationResponse;
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

    //todo: pagination will added
    @GetMapping
    public PageDefectLocationResponse getAllDefectLocation() {
        return defectLocationService.findAll();
    }

    @PostMapping
    public DefectLocationResponse addNewLocations(@RequestBody DefectLocationRequest defectLocationRequest) {
        return defectLocationService.addNewLocation(defectLocationRequest);
    }

    //todo: crud
    //todo: get all locaiton about to image

    @DeleteMapping()
    public List<Long> removeLocations(@RequestBody LocationRemoveRequest locationRemoveRequest) {
        return defectLocationService.removeLocation(locationRemoveRequest);
    }

}
