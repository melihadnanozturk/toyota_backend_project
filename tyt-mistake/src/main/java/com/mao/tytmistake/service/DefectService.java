package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;

public interface DefectService {

    PageDefectResponse getAllDefect();

    Long removeDefect(Long id);

    DefectResponse updateDefect(UpdateDefectRequest updateDefectRequest);

    DefectResponse addNewDefect(DefectRequest defectRequest);

    DefectEntity getById(Long id);
}
