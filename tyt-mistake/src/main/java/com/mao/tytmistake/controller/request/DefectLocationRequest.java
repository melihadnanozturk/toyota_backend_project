package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DefectLocationRequest {

    @NotNull
    private List<LocationsRequest> locations;

    @NotNull
    private Long vehicleDefectEntityId;
}
