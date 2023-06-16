package com.mao.tytmistake.controller.request.page;

import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVehicleRequest extends TytPageRequest {

    private Model model;
    private Colour colour;
    @Builder.Default
    private VehicleSortColon sortCol = VehicleSortColon.ID;

}
