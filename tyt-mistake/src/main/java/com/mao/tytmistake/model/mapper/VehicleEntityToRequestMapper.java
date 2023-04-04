package com.mao.tytmistake.model.mapper;

import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleEntityToRequestMapper {

    VehicleResponse entityToRequest(VehicleEntity vehicleEntity);
}
