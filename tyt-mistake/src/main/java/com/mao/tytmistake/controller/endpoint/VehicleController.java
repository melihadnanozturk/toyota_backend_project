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
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final GetAllService getAllService;

    @GetMapping
    public BaseResponse<List<PageVehicleResponse>> getAllVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody PageVehicleRequest pageVehicleRequest) {
        Page<PageVehicleResponse> page = getAllService.getAllVehicle(headers, pageVehicleRequest);
        return BaseResponse.isSuccess(page.getContent());
    }

    @PostMapping
    public BaseResponse<VehicleResponse> addNewVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid VehicleRequest vehicleRequest) {
        VehicleResponse response = vehicleService.addNewVehicle(headers, vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    @PutMapping("/{id}")
    public BaseResponse<VehicleResponse> updateVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody VehicleRequest vehicleRequest,
            @PathVariable Long id) {
        VehicleResponse response = vehicleService.updateVehicle(headers, id, vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    @DeleteMapping("/{id}")
    public BaseResponse<Long> removeVehicle(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long id) {
        Long removedId = vehicleService.removeVehicle(headers, id);
        return BaseResponse.isSuccess(removedId);
    }
}
