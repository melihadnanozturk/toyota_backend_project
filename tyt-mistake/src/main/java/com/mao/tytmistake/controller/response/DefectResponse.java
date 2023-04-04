package com.mao.tytmistake.controller.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DefectResponse {

    private Long id;
    private String defectCode;
    private String defectDesc;
}
