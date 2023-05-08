package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.UpdateVehicleRequest;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public PageVehicleResponse getAllVehicle(@RequestBody PageVehicleRequest pageVehicleRequest) {
        return vehicleService.getAllVehicle(pageVehicleRequest);
    }

    @PostMapping
    public VehicleResponse addNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.newVehicleAdd(vehicleRequest);
    }

    //todo: id yi url den mi alalaım yokas body den mi gelsin düşün?
    @PutMapping
    public VehicleResponse updateVehicle(@RequestBody UpdateVehicleRequest vehicleRequest) {
        return vehicleService.updateVehicle(vehicleRequest);
    }

    @DeleteMapping("/{id}")
    public Long removeVehicle(@PathVariable Long id) {
        return vehicleService.removeVehicle(id);
    }
}
