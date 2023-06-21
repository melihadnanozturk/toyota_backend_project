package com.mao.tytmistake.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to response location information.
 * This class contains information field about to location
 * The class uses the Lombok annotations @Setter, @Getter and @Builder to automatically generate getters,setters and a builders
 */
@Getter
@Setter
@Builder
public class DefectLocationResponse {

    List<LocationsResponse> locationsResponseList;

    /**
     * Maps the LocationsResponses to the DefectLocationResponse
     *
     * @param locationsResponses LocationsResponses to be mapped
     * @return DefectLocationResponse holding information mapped from locationsResponses
     */
    public static DefectLocationResponse defectLocationEntityMappedResponse(List<LocationsResponse> locationsResponses) {
        return DefectLocationResponse.builder()
                .locationsResponseList(locationsResponses)
                .build();
    }
}
