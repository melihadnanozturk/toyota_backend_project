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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vd")
@RequiredArgsConstructor
public class VehicleDefectController {

    private final VehicleDefectService vehicleDefectService;
    private final GetAllService getAllService;

    @GetMapping
    public BaseResponse<Page<PageVehicleDefectResponse>> getAllVehicleDefect(@RequestBody PageVehicleDefectRequest request) {
        return getAllService.getAllVehicleDefect(request);
    }

    @PostMapping
    public BaseResponse<VehicleDefectResponse> addNewVehicleDefect(@RequestBody VehicleDefectRequest vehicleDefectRequest) {
        return vehicleDefectService.addNewVehicleDefect(vehicleDefectRequest);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleDefectResponse> updateVehicleDefect(@RequestBody UpdateVehicleDefectRequest request, @PathVariable Long id) {
        return vehicleDefectService.updateVehicleDefect(request, id);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> delete(@PathVariable Long id) {
        return vehicleDefectService.deleteVehicleDefect(id);
    }
}
