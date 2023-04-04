package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;

public interface VehicleDefectService {

    PageVehicleDefectResponse getAllVehicleDefect();

    VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest defectRequest);

    void deleteVehicleDefect(Long id);

    VehicleDefectResponse updateVehicleDefect(Long id, VehicleDefectRequest vehicleDefectRequest);
}
