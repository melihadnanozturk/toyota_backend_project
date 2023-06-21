package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to query location information
 * This class is POJO class that contains defect id and locations.
 * The class uses the Lombok annotations @Getter, @Setter to automatically generate getters and setters
 */
@Getter
@Setter
public class DefectLocationRequest {

    @NotNull
    private Long defectId;

    @NotNull
    private List<LocationsRequest> locations;

}
