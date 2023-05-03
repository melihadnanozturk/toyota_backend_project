package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;

public interface VehicleDefectService {

    PageVehicleDefectResponse getAllVehicleDefect();

    VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest defectRequest);

    Long deleteVehicleDefect(Long id);

    VehicleDefectResponse updateVehicleDefect(UpdateVehicleDefectRequest vehicleDefectRequest);

    VehicleDefectEntity getVehicleDefectEntityById(Long id);
}
