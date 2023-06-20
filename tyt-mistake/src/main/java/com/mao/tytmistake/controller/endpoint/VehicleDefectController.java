package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vd")
@RequiredArgsConstructor
public class VehicleDefectController {

    private final VehicleDefectService vehicleDefectService;
    private final GetAllService getAllService;

    @GetMapping
    public BaseResponse<List<PageVehicleDefectResponse>> getAllDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody PageDefectRequest request) {
        Page<PageVehicleDefectResponse> page = getAllService.getAllDefect(headers, request);
        return BaseResponse.isSuccess(page.getContent());
    }

    @PostMapping
    public BaseResponse<VehicleDefectResponse> addNewDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody VehicleDefectRequest vehicleDefectRequest) {
        VehicleDefectResponse response = vehicleDefectService.addNewDefect(headers, vehicleDefectRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleDefectResponse> updateDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody UpdateVehicleDefectRequest request,
            @PathVariable Long id) {
        VehicleDefectResponse response = vehicleDefectService.updateDefect(headers, request, id);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> removeDefect(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long id) {
        Long removedId = vehicleDefectService.deleteDefect(headers, id);
        return BaseResponse.isSuccess(removedId);
    }
}
