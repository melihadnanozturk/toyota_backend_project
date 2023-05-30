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
        return getAllService.getAllVehicle(pageVehicleRequest);
    }

    @PostMapping
    public BaseResponse<VehicleResponse> addNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.addNewVehicle(vehicleRequest);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleResponse> updateVehicle(@RequestBody VehicleRequest vehicleRequest, @PathVariable Long id) {
        return vehicleService.updateVehicle(id, vehicleRequest);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> removeVehicle(@PathVariable Long id) {
        return vehicleService.removeVehicle(id);
    }
}
