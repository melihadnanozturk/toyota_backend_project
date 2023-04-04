package com.mao.tytmistake.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PageVehicleResponse {

    private List<VehicleResponse> vehicleResponseList;
}
