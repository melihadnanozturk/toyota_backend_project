package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.model.entity.LocationEntity;
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

    public static LocationsResponse mappedLocationsResponse(LocationEntity locationEntity) {
        return LocationsResponse.builder()
                .id(locationEntity.getId())
                .xLocation(locationEntity.getXLocation())
                .yLocation(locationEntity.getYLocation())
                .build();
    }
}
