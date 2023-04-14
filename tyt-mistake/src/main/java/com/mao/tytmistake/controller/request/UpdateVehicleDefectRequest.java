package com.mao.tytmistake.controller.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVehicleDefectRequest {
    private Long id;
    private VehicleDefectRequest vehicleDefectRequest;
}
