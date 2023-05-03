package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.model.entity.DefectLocationEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationsResponse {

    private Long id;

    private String yLocation;

    private String xLocation;

    public static LocationsResponse mappedLocationsResponse(DefectLocationEntity defectLocationEntity) {
        return LocationsResponse.builder()
                .id(defectLocationEntity.getId())
                .xLocation(defectLocationEntity.getXLocation())
                .yLocation(defectLocationEntity.getYLocation())
                .build();
    }
}
