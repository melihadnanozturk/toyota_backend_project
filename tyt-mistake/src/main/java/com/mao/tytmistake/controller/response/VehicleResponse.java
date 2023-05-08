package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class VehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private Colour colour;
    //will adding
    private Integer defectNumbers;
    protected LocalDate createdAt;
    protected LocalDate updatedAt;

    public static VehicleResponse vehicleEntityMappedResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .createdAt(vehicleEntity.getCreatedAt())
                .updatedAt(vehicleEntity.getUpdatedAt())
                .build();
    }
}
