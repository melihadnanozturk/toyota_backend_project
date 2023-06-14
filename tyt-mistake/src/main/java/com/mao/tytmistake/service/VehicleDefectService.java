package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import org.springframework.http.HttpHeaders;

public interface VehicleDefectService {

    VehicleDefectResponse addNewVehicleDefect(HttpHeaders headers, VehicleDefectRequest request);

    Long deleteVehicleDefect(HttpHeaders headers, Long id);

    VehicleDefectResponse updateVehicleDefect(HttpHeaders headers, UpdateVehicleDefectRequest request, Long id);

    VehicleDefectEntity getVehicleDefectEntityById(Long id);

    Integer getDefectNumbersByVehicleId(Long vehicleId);
}
