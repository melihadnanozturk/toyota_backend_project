package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class VehicleRequest {

    @NotBlank
    private String model;
    private String chassisNumber;
    private String colour;
}
