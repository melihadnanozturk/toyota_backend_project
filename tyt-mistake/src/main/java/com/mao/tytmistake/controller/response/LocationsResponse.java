package com.mao.tytmistake.controller.response;

import com.mao.tytmistake.model.entity.LocationEntity;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to response location information.
 * This class contains information field about to location
 * The class uses the Lombok annotations @Setter, @Getter and @Builder to automatically generate getters,setters and a builders,@EqualsAndHashCode to equals process.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class LocationsResponse {

    private Long id;

    private String yLocation;

    private String xLocation;

    /**
     * Maps the LocationEntity to the LocationsResponse
     *
     * @param locationEntity LocationEntity to be mapped
     * @return LocationsResponse holding information mapped from locationEntity
     */
    public static LocationsResponse mappedLocationsResponse(LocationEntity locationEntity) {
        return LocationsResponse.builder()
                .id(locationEntity.getId())
                .xLocation(locationEntity.getXLocation())
                .yLocation(locationEntity.getYLocation())
                .build();
    }
}
