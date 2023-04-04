package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class UpdateVehicleRequest {

    @NotNull
    private Long id;

    @NotNull
    VehicleRequest vehicleRequest;
}
