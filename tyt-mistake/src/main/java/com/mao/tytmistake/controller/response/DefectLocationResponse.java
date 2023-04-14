package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.model.entity.DefectLocationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefectLocationResponse {

    private Long id;
    private String yLocation;
    private String xLocation;

    public static DefectLocationResponse defectLocationEntityMappedResponse(DefectLocationEntity defectLocationEntity) {
        return DefectLocationResponse.builder()
                .id(defectLocationEntity.getId())
                .xLocation(defectLocationEntity.getXLocation())
                .yLocation(defectLocationEntity.getYLocation())
                .build();
    }
}
