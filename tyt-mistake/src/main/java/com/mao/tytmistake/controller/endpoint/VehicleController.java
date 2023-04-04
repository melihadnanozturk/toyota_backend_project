package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.PageVehicleResponse;
import com.mao.tytmistake.controller.response.VehicleResponse;
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
    public PageVehicleResponse getAllVehicle() {
        //todo: will add pagination
        //todo Burada filtreleme getir {model/ renk/ hata}}.
        //todo:Birden fazla filtrelemeyi kabul eden bir yapı.
        return vehicleService.getAllVehicle();
    }

    @PostMapping
    public VehicleResponse addNewVehicle(@RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.newVehicleAdd(vehicleRequest);
    }

    @PutMapping("/{id}")
    public VehicleResponse updateVehicle(@PathVariable Long id, @RequestBody @Valid VehicleRequest vehicleRequest) {
        return vehicleService.updateVehicle(id, vehicleRequest);
    }

    @DeleteMapping
    public void removeVehicle(@PathVariable Long id) {
        //todo: Tüm silme işlemlerinde güvenlik problemini çöz
        //todo: soft delete
        vehicleService.removeVehicle(id);
    }
}
