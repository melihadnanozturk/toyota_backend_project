package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class VehicleDefectResponse {

    private Long defectId;
    private Defect defect;
    private String vehicleDefectDesc;
    private Long vehicleId;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static VehicleDefectResponse vehicleDefectEntityMappedResponse(VehicleDefectEntity vehicleDefectEntity) {
        return VehicleDefectResponse.builder()
                .defectId(vehicleDefectEntity.getId())
                .vehicleId(vehicleDefectEntity.getVehicle().getId())
                .vehicleDefectDesc(vehicleDefectEntity.getVehicleDefectDesc())
                .defect(vehicleDefectEntity.getDefect())
                .createdAt(vehicleDefectEntity.getCreatedAt())
                .updatedAt(vehicleDefectEntity.getUpdatedAt())
                .build();
    }
}
