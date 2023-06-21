package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.model.entity.LocationEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to query locations
 * This class is POJO class that contains location information .
 * The class uses the Lombok annotations @Getter, @Setter and @Builder to automatically generate getters, setters and a builder
 */
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
