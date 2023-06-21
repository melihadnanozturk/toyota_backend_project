package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This class is used to response defect information.
 * This class contains information field about to defect.
 * The class uses the Lombok annotations @Getter,@Setter and @Builder to automatically generate getters, setters and a builders,@EqualsAndHashCode to equals process.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class DefectResponse {

    private Long defectId;
    private Defect defect;
    private String vehicleDefectDesc;
    private Long vehicleId;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    /**
     * Maps the DefectEntity to the DefectResponse
     *
     * @param defectEntity DefectEntity to be mapped
     * @return DefectResponse holding information mapped from defectEntity
     */
    public static DefectResponse vehicleDefectEntityMappedResponse(DefectEntity defectEntity) {
        return DefectResponse.builder()
                .defectId(defectEntity.getId())
                .vehicleId(defectEntity.getVehicle().getId())
                .vehicleDefectDesc(defectEntity.getDefectDesc())
                .defect(defectEntity.getDefect())
                .createdAt(defectEntity.getCreatedAt())
                .updatedAt(defectEntity.getUpdatedAt())
                .build();
    }
}
