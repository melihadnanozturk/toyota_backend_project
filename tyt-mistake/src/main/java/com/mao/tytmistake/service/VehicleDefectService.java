package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.http.HttpHeaders;

public interface VehicleDefectService {

    VehicleDefectResponse addNewDefect(HttpHeaders headers, VehicleDefectRequest request);

    Long deleteDefect(HttpHeaders headers, Long id);

    VehicleDefectResponse updateDefect(HttpHeaders headers, UpdateVehicleDefectRequest request, Long id);

    DefectEntity getDefectEntityById(Long id);
}
