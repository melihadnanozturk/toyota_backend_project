package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This class is used to response defect information in page.
 * This class contains TerminalResponses
 * The class uses the Lombok annotations @Setter,  @Getter and @Builder to automatically generate getters and a builder method.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class PageDefectResponse {

    private Long defectId;
    private Defect defect;
    private String vehicleDefectDesc;
    private Long vehicleId;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    /**
     * Maps the DefectEntity to the PageDefectResponse
     *
     * @param defectEntity DefectEntity to be mapped
     * @return PageDefectResponse holding information mapped from defectEntity
     */
    public static PageDefectResponse vehicleDefectEntityMappedPageResponse(DefectEntity defectEntity) {
        return PageDefectResponse.builder()
                .defectId(defectEntity.getId())
                .vehicleId(defectEntity.getVehicle().getId())
                .vehicleDefectDesc(defectEntity.getDefectDesc())
                .defect(defectEntity.getDefect())
                .createdAt(defectEntity.getCreatedAt())
                .updatedAt(defectEntity.getUpdatedAt())
                .build();
    }
}
