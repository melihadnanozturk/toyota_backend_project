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

/**
 * This Controller used to create, update, delete and retrieve Vehicle
 */
@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;
    private final GetAllService getAllService;

    /**
     * Retrieve Vehicle locations
     *
     * @param headers            UserName, Bearer Token
     * @param pageVehicleRequest Request for pagination
     * @return PageVehicleResponse contain vehicle information
     */
    @GetMapping
    public BaseResponse<List<PageVehicleResponse>> getAllVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody PageVehicleRequest pageVehicleRequest) {
        Page<PageVehicleResponse> page = getAllService.getAllVehicle(headers, pageVehicleRequest);
        return BaseResponse.isSuccess(page.getContent());
    }

    /**
     * Create new vehicle
     *
     * @param headers        UserName, Bearer Token
     * @param vehicleRequest Vehicle information to register
     * @return VehicleResponse with created vehicle information
     */
    @PostMapping
    public BaseResponse<VehicleResponse> addNewVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody @Valid VehicleRequest vehicleRequest) {
        VehicleResponse response = vehicleService.addNewVehicle(headers, vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Update exits vehicle
     *
     * @param headers        UserName, Bearer Token
     * @param vehicleRequest Vehicle information to update
     * @param vehicleId      Vehicle id to update
     * @return VehicleResponse with updated vehicle information
     */
    @PutMapping("/{vehicleId}")
    public BaseResponse<VehicleResponse> updateVehicle(
            @RequestHeader HttpHeaders headers,
            @RequestBody VehicleRequest vehicleRequest,
            @PathVariable Long vehicleId) {
        VehicleResponse response = vehicleService.updateVehicle(headers, vehicleId, vehicleRequest);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Remove exits vehicle
     *
     * @param headers   UserName, Bearer Token
     * @param vehicleId Vehicle information to remove
     * @return Long that removed vehicle id
     */
    @DeleteMapping("/{vehicleId}")
    public BaseResponse<Long> removeVehicle(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long vehicleId) {
        Long removedId = vehicleService.removeVehicle(headers, vehicleId);
        return BaseResponse.isSuccess(removedId);
    }
}
