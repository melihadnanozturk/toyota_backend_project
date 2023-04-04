package com.mao.tytmistake.model.mapper;

import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DefectEntityToRequestMapper {

    DefectResponse entityToResponse(DefectEntity defectEntity);
}
