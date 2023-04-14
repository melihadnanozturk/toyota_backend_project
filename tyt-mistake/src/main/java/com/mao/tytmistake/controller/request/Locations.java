package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.DefectLocationEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Locations {

    @NotBlank
    private String yLocation;

    @NotBlank
    private String xLocation;

    public static DefectLocationEntity mappedDefectLocationEntity(Locations location) {
        return DefectLocationEntity.builder()
                .xLocation(location.getXLocation())
                .yLocation(location.getYLocation())
                .build();
    }
}
