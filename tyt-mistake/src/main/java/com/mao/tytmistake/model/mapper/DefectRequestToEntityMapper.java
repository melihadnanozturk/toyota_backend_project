package com.mao.tytmistake.model.mapper;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.model.entity.DefectEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DefectRequestToEntityMapper {

    DefectEntity requestToEntity(DefectRequest defectRequest);

}
