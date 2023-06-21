package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to query defect
 * This class is POJO class that contains defect information.
 * The class uses the Lombok annotations @Getter, @Setter and @Builder to automatically generate getters, setters and a builder
 */
@Builder
@Getter
@Setter
public class DefectRequest {

    @NotNull
    private Defect defect;

    private String vehicleDefectDesc;

    @NotNull
    private Long vehicleId;

    public static DefectEntity responseMapToVehicleDefectEntity(DefectRequest defectRequest) {
        return DefectEntity.builder()
                .defect(defectRequest.getDefect())
                .defectDesc(defectRequest.getVehicleDefectDesc())
                .build();
    }
}
