package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class VehicleDefectResponse {

    private Long id;
    private String defectImage;
    private VehicleResponse vehicle;
    //todo: buryı kontrol et.Nested yapı oluyor
    private DefectResponse defect;
    private List<DefectLocationResponse> defectLocation;

    public static VehicleDefectResponse vehicleDefectEntityMappedResponse(VehicleDefectEntity vehicleDefectEntity) {
        List<DefectLocationResponse> defectLocationResponses = vehicleDefectEntity.getDefectLocation()
                .stream().map(DefectLocationResponse::defectLocationEntityMappedResponse).toList();

        DefectResponse defectResponse = DefectResponse.defectEntityMappedResponse(vehicleDefectEntity.getDefect());

        VehicleResponse vehicleResponse = VehicleResponse.vehicleEntityMappedResponse(vehicleDefectEntity.getVehicle());

        return VehicleDefectResponse.builder()
                .id(vehicleDefectEntity.getId())
                .defectImage(vehicleDefectEntity.getDefectImage())
                .defect(defectResponse)
                .vehicle(vehicleResponse)
                .defectLocation(defectLocationResponses)
                .build();
    }
}
