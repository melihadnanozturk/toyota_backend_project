package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefectRequest {

    @NotBlank
    private String defectCode;

    private String defectDesc;
}
