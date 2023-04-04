package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageVehicleDefectResponse {

    private List<VehicleDefectEntity> defectEntities;
}
