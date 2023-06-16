package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.LocationEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocationsRequest {

    @NotBlank
    private String yLocation;

    @NotBlank
    private String xLocation;

    public static LocationEntity mappedDefectLocationEntity(LocationsRequest location) {
        return LocationEntity.builder()
                .xLocation(location.getXLocation())
                .yLocation(location.getYLocation())
                .build();
    }
}
