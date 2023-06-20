package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class VehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private Colour colour;

    public static VehicleResponse vehicleEntityMappedResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .build();
    }
}
