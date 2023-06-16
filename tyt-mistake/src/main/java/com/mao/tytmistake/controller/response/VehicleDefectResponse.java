package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.DefectEntity;
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

    public static VehicleDefectResponse vehicleDefectEntityMappedResponse(DefectEntity defectEntity) {
        return VehicleDefectResponse.builder()
                .defectId(defectEntity.getId())
                .vehicleId(defectEntity.getVehicle().getId())
                .vehicleDefectDesc(defectEntity.getDefectDesc())
                .defect(defectEntity.getDefect())
                .createdAt(defectEntity.getCreatedAt())
                .updatedAt(defectEntity.getUpdatedAt())
                .build();
    }
}
