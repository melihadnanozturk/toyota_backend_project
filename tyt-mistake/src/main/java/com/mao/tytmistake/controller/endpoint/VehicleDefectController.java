package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vd")
@RequiredArgsConstructor
public class VehicleDefectController {

    private final VehicleDefectService vehicleDefectService;
    private final GetAllService getAllService;

    @GetMapping
    public BaseResponse<Page<PageVehicleDefectResponse>> getAllVehicleDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody PageVehicleDefectRequest request) {
        Page<PageVehicleDefectResponse> page = getAllService.getAllVehicleDefect(headers, request);
        return BaseResponse.isSuccess(page);
    }

    @PostMapping
    public BaseResponse<VehicleDefectResponse> addNewVehicleDefect(@RequestBody VehicleDefectRequest vehicleDefectRequest) {
        VehicleDefectResponse response = vehicleDefectService.addNewVehicleDefect(vehicleDefectRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleDefectResponse> updateVehicleDefect(@RequestBody UpdateVehicleDefectRequest request, @PathVariable Long id) {
        VehicleDefectResponse response = vehicleDefectService.updateVehicleDefect(request, id);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> delete(@PathVariable Long id) {
        Long removedId = vehicleDefectService.deleteVehicleDefect(id);
        return BaseResponse.isSuccess(removedId);
    }
}
