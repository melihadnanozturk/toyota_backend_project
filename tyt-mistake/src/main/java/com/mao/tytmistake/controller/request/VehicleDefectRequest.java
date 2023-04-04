package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleDefectRequest {

    private String defectImage;

    private String vehicleDefectDesc;

    @NotNull
    private Long vehicleId;

    @NotNull
    private Long defectId;

    private String yLocation;

    private String xLocation;
}
