package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;

public interface VehicleDefectService {

    BaseResponse<VehicleDefectResponse> addNewVehicleDefect(VehicleDefectRequest request);

    BaseResponse<Long> deleteVehicleDefect(Long id);

    BaseResponse<VehicleDefectResponse> updateVehicleDefect(UpdateVehicleDefectRequest request, Long id);

    VehicleDefectEntity getVehicleDefectEntityById(Long id);

    Integer getDefectNumbersByVehicleId(Long vehicleId);
}
