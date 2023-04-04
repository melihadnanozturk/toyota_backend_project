package com.mao.tytmistake.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VehicleResponse {

    private Long id;
    private String model;
    private String chassisNumber;
    private String colour;
}
