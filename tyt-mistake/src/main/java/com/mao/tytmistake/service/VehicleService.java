package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface VehicleService {

    BaseResponse<VehicleResponse> addNewVehicle(VehicleRequest vehicleRequest);

    BaseResponse<Long> removeVehicle(@PathVariable Long id);

    BaseResponse<VehicleResponse> updateVehicle(Long id, VehicleRequest vehicleRequest);

    VehicleEntity getById(Long id);
}