package com.mao.tytmistake.controller.request.page;

import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageDefectRequest extends TytPageRequest {

    private Defect defect;
    private Long vehicleId;
    @Builder.Default
    private DefectSortCol sortCol = DefectSortCol.ID;
}
