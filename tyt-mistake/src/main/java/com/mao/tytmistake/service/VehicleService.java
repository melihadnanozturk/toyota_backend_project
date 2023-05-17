package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

public interface VehicleService {

    Page<PageVehicleResponse> getAllVehicle(PageVehicleRequest pageVehicleRequest);

    VehicleResponse newVehicleAdd(VehicleRequest vehicleRequest);

    Long removeVehicle(@PathVariable Long id);

    VehicleResponse updateVehicle(Long id, VehicleRequest vehicleRequest);

    VehicleEntity getById(Long id);
}