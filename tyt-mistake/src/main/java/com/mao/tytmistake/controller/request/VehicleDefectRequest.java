package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleDefectRequest {

    @NotNull
    private Defect defect;

    private String vehicleDefectDesc;

    @NotBlank
    private String defectImage;

    @NotNull
    private Long vehicleId;

    public static VehicleDefectEntity responseMapToVehicleDefectEntity(VehicleDefectRequest defectRequest) {
        return VehicleDefectEntity.builder()
                .defect(defectRequest.getDefect())
                .defectImage(defectRequest.getDefectImage())
                .vehicleDefectDesc(defectRequest.getVehicleDefectDesc())
                .build();
    }
}
