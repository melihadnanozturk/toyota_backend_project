package com.mao.tytmistake.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageDefectResponse {

    List<DefectResponse> defectResponses;
}
