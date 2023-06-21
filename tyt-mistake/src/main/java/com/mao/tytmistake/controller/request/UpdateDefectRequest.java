package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


/**
 * This class is used to update query defect
 * This class is POJO class that contains defectDesc nad defectImage.
 * The class uses the Lombok annotations @Getter, @Setter to automatically generate getters and setters
 */
@Getter
@Setter
public class UpdateDefectRequest {

    private String defectDesc;

    @NotBlank
    private String defectImage;
}
