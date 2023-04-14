package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.Model;
import com.mao.tytmistake.model.entity.VehicleEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private String colour;

    public static VehicleResponse vehicleEntityMappedResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .build();
    }
}
