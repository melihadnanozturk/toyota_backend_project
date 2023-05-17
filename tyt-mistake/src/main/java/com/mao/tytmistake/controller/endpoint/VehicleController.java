package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.service.VehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public Page<PageVehicleResponse> getAllVehicle(@RequestBody PageVehicleRequest pageVehicleRequest) {
        return vehicleService.getAllVehicle(pageVehicleRequest);
    }

    @PostMapping
    public VehicleResponse addNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.newVehicleAdd(vehicleRequest);
    }

    //todo: id yi url den mi alalaım yokas body den mi gelsin düşün?
    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(@RequestBody VehicleRequest vehicleRequest, @PathVariable Long id) {
        return vehicleService.updateVehicle(id, vehicleRequest);
    }

    @DeleteMapping("/{id}")
    public Long removeVehicle(@PathVariable Long id) {
        return vehicleService.removeVehicle(id);
    }
}
