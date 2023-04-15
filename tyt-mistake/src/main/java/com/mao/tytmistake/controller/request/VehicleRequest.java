package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.Colour;
import com.mao.tytmistake.model.entity.Model;
import com.mao.tytmistake.model.entity.VehicleEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class VehicleRequest {

    @NotNull
    private Model model;
    private String chassisNumber;
    private Colour colour;

    public static VehicleEntity requestMappedVehicleEntity(VehicleRequest vehicleRequest) {
        return VehicleEntity.builder()
                .model(vehicleRequest.getModel())
                .chassisNumber(vehicleRequest.getChassisNumber())
                .colour(vehicleRequest.getColour())
                .build();
    }
}
