package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.VehicleEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class VehicleRequest {

    @NotBlank
    private String model;
    private String chassisNumber;
    private String colour;

    public static VehicleEntity requestMappedVehicleEntity(VehicleRequest vehicleRequest) {
        return VehicleEntity.builder()
                .model(vehicleRequest.getModel())
                .chassisNumber(vehicleRequest.getChassisNumber())
                .colour(vehicleRequest.getColour())
                .build();
    }
}
