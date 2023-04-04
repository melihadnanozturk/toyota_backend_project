package com.mao.tytmistake.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleDefectResponse {

    private Long id;
    private String defectImage;
    private VehicleResponse vehicle;
    //todo: buryı kontrol et.Nested yapı oluyor
    private DefectResponse defect;
    private DefectLocationResponse defectLocation;
}
