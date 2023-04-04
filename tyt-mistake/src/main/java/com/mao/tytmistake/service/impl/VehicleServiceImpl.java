package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.PageVehicleResponse;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    @Override
    public PageVehicleResponse getAllVehicle() {
        return PageVehicleResponse.builder()
                .vehicleResponseList(vehicleEntityRepository.findAll()
                        .stream().map(this::mapToResponse).toList())
                .build();
    }

    @Override
    public VehicleResponse newVehicleAdd(VehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = mapToEntity(vehicleRequest);
        VehicleEntity saved = vehicleEntityRepository.save(vehicleEntity);
        return mapToResponse(saved);
    }

    @Override
    public VehicleResponse updateVehicle(Long id, VehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = checkVehicleExist(id);
        VehicleEntity updated = setVehicle(vehicleEntity, vehicleRequest);
        return mapToResponse(vehicleEntityRepository.save(updated));
    }

    @SneakyThrows
    private VehicleEntity checkVehicleExist(Long id) {
        return vehicleEntityRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));
    }

    @Override
    public void removeVehicle(Long id) {
        vehicleEntityRepository.deleteById(id);
    }

    @Override
    public VehicleEntity getById(Long id) {
        return checkVehicleExist(id);
    }

    private VehicleEntity setVehicle(VehicleEntity vehicleEntity, VehicleRequest vehicleRequest) {
        vehicleEntity.setColour(vehicleRequest.getColour());
        vehicleEntity.setChassisNumber(vehicleRequest.getChassisNumber());
        vehicleEntity.setModel(vehicleRequest.getModel());
        return vehicleEntity;
    }

    private VehicleEntity mapToEntity(VehicleRequest vehicleRequest) {
        return VehicleEntity.builder()
                .model(vehicleRequest.getModel())
                .chassisNumber(vehicleRequest.getChassisNumber())
                .colour(vehicleRequest.getColour())
                .build();
    }

    private VehicleResponse mapToResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .build();
    }

}
