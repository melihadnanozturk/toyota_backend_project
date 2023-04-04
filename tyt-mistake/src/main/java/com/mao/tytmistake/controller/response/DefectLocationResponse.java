package com.mao.tytmistake.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefectLocationResponse {

    private Long id;
    private String yLocation;
    private String xLocation;
}
