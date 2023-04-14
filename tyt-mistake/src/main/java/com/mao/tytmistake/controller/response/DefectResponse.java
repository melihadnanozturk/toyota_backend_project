package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.DefectEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DefectResponse {

    private Long id;
    private String defectCode;
    private String defectDesc;

    public static DefectResponse defectEntityMappedResponse(DefectEntity defectEntity) {
        return DefectResponse.builder()
                .id(defectEntity.getId())
                .defectCode(defectEntity.getDefectCode())
                .defectDesc(defectEntity.getDefectDesc())
                .build();
    }
}
