package com.mao.tytmistake.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class PageVehicleDefectResponse {

    private Page<VehicleDefectResponse> defectEntities;
}
