package com.mao.tytmistake.controller.response.page;

import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class PageVehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private Colour colour;
    //will adding
    private Integer defectNumbers;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static PageVehicleResponse vehicleEntityMappedPageResponse(VehicleEntity vehicleEntity) {
        return PageVehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .createdAt(vehicleEntity.getCreatedAt())
                .updatedAt(vehicleEntity.getUpdatedAt())
                .build();
    }
}
