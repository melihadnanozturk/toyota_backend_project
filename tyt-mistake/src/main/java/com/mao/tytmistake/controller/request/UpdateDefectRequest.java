package com.mao.tytmistake.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateDefectRequest {

    @NotNull
    private Long id;

    @NotNull
    private DefectRequest defectRequest;
}
