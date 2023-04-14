package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vd")
@RequiredArgsConstructor
public class VehicleDefectController {

    private final VehicleDefectService vehicleDefectService;

    @GetMapping
    public PageVehicleDefectResponse getAllVehicleDefect() {
        return vehicleDefectService.getAllVehicleDefect();
    }

    @PostMapping
    public VehicleDefectResponse addNewVehicleDefect(@RequestBody VehicleDefectRequest vehicleDefectRequest) {
        return vehicleDefectService.addNewVehicleDefect(vehicleDefectRequest);
    }

    @PutMapping
    public VehicleDefectResponse updateVehicleDefect(@RequestBody UpdateVehicleDefectRequest vehicleDefectRequest) {
        return vehicleDefectService.updateVehicleDefect(vehicleDefectRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehicleDefectService.deleteVehicleDefect(id);
    }
}
