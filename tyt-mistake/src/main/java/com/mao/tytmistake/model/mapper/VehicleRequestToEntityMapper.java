package com.mao.tytmistake.model.mapper;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.model.entity.VehicleEntity;
import org.mapstruct.Mapper;

@Mapper
public interface VehicleRequestToEntityMapper {

    VehicleEntity requestToEntity(VehicleRequest vehicleRequest);
}
