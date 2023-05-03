package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VehicleDefectResponse {

    private Long id;
    private VehicleResponse vehicle;
    private Defect defect;
    private String defectImage;

    public static VehicleDefectResponse vehicleDefectEntityMappedResponse(VehicleDefectEntity vehicleDefectEntity) {
        VehicleResponse vehicleResponse = VehicleResponse.vehicleEntityMappedResponse(vehicleDefectEntity.getVehicle());

        return VehicleDefectResponse.builder()
                .id(vehicleDefectEntity.getId())
                .defectImage(vehicleDefectEntity.getDefectImage())
                .defect(vehicleDefectEntity.getDefect())
                .vehicle(vehicleResponse)
                .build();
    }
}
