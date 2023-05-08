package com.mao.tytmistake.controller.request.page;

import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageVehicleRequest extends PageRequest {

    private Model model;

    private Colour colour;

}
