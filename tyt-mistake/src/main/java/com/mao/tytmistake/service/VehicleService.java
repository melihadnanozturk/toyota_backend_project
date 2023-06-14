package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.http.HttpHeaders;

public interface VehicleService {

    VehicleResponse addNewVehicle(HttpHeaders headers, VehicleRequest vehicleRequest);

    Long removeVehicle(HttpHeaders headers, Long id);

    VehicleResponse updateVehicle(HttpHeaders headers, Long id, VehicleRequest vehicleRequest);

    VehicleEntity getById(Long id);
}