package com.mao.tytmistake.controller.request.page;

import com.mao.tytmistake.model.entity.enums.Defect;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a page request for retrieving a list of defects with pagination.
 */
@Getter
@Setter
public class PageDefectRequest extends TytPageRequest {

    private Defect defect;
    private Long vehicleId;
    @Builder.Default
    private DefectSortCol sortCol = DefectSortCol.ID;
}
