package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final GetAllService getAllService;

    @GetMapping
    public BaseResponse<Page<PageVehicleResponse>> getAllVehicle(@RequestBody PageVehicleRequest pageVehicleRequest) {
        Page<PageVehicleResponse> page = getAllService.getAllVehicle(pageVehicleRequest);
        return BaseResponse.isSuccess(page);
    }

    @PostMapping
    public BaseResponse<VehicleResponse> addNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) {
        VehicleResponse response = vehicleService.addNewVehicle(vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleResponse> updateVehicle(@RequestBody VehicleRequest vehicleRequest, @PathVariable Long id) {
        VehicleResponse response = vehicleService.updateVehicle(id, vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> removeVehicle(@PathVariable Long id) {
        Long removedId = vehicleService.removeVehicle(id);
        return BaseResponse.isSuccess(removedId);
    }
}
