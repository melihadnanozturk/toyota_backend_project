package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class VehicleDefectRequest {

    @NotBlank
    private String defectImage;

    private String vehicleDefectDesc;

    @NotNull
    private Long vehicleId;

    @NotNull
    private Long defectId;

    //todo: buraya dikkat
    private List<Locations> locaitons;

    public static VehicleDefectEntity responseMapToVehicleDefectEntity(VehicleDefectRequest defectRequest) {
        return VehicleDefectEntity.builder()
                .defectImage(defectRequest.getDefectImage())
                .vehicleDefectDesc(defectRequest.getVehicleDefectDesc())
                .build();
    }
}
