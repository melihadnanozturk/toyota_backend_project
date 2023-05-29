package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateVehicleDefectRequest {

    private String defectDesc;

    @NotBlank
    private String defectImage;
}
