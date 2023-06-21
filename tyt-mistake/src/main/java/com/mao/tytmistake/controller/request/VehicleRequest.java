package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to query vehicle
 * This class is POJO class that contains vehicle information.
 * The class uses the Lombok annotations @Getter, @Setter and @Builder to automatically generate getters, setters and a builder
 */
@Builder
@Getter
@Setter
public class VehicleRequest {

    @NotNull(message = "model can not be null")
    private Model model;
    @NotNull(message = "chassisNumber can not be null")
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
