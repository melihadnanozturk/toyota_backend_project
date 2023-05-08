package com.mao.tytmistake.controller.request.page;

import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVehicleDefectRequest extends PageRequest {

    private Defect defect;

    private String defectDesc;

    //@NotNull(message = "Vehicle Id Cannot Be Null !")
    private Long vehicleId;
}
