package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.DefectEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefectRequest {

    @NotBlank
    private String defectCode;

    private String defectDesc;

    public static DefectEntity requestMappedDefectEntity(DefectRequest defectRequest) {
        return DefectEntity.builder()
                .defectCode(defectRequest.getDefectCode())
                .defectDesc(defectRequest.getDefectDesc())
                .build();
    }

}
