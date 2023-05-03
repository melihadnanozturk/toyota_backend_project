package com.mao.tytmistake.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class DefectLocationResponse {

    List<LocationsResponse> locationsResponseList;

    public static DefectLocationResponse defectLocationEntityMappedResponse(List<LocationsResponse> locationsResponses) {
        return DefectLocationResponse.builder()
                .locationsResponseList(locationsResponses)
                .build();
    }
}
