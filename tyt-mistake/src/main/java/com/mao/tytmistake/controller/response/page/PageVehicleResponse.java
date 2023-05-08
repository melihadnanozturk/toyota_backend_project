package com.mao.tytmistake.controller.response.page;

import com.mao.tytmistake.controller.response.VehicleResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Builder
@Getter
@Setter
public class PageVehicleResponse {

    private Page<VehicleResponse> vehicleResponseList;
}
