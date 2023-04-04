package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.*;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.DefectLocationEntity;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.DefectService;
import com.mao.tytmistake.service.VehicleDefectService;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDefectImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleService vehicleService;
    private final DefectService defectService;


    @Override
    public PageVehicleDefectResponse getAllVehicleDefect() {
        return PageVehicleDefectResponse.builder()
                .defectEntities(vehicleDefectEntityRepository.findAll().stream().toList())
                .build();
    }

    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest vehicleDefectRequest) {
        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getDefectId());
        DefectEntity defectEntity = defectService.getById(vehicleDefectRequest.getDefectId());
        DefectLocationEntity defectLocationEntity = mapToDefectEntity(vehicleDefectRequest);

        VehicleDefectEntity vehicleDefectEntity = mapToEntity(vehicleDefectRequest);
        vehicleDefectEntity.setVehicle(vehicleEntity);
        vehicleDefectEntity.setDefect(defectEntity);
        vehicleDefectEntity.setDefectLocation(defectLocationEntity);

        return mapToResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(Long id, VehicleDefectRequest vehicleDefectRequest) {
        return null;
    }

    @Override
    public void deleteVehicleDefect(Long id) {
        vehicleDefectEntityRepository.deleteById(id);
    }

    private VehicleDefectResponse mapToResponse(VehicleDefectEntity vehicleDefectEntity) {
        DefectResponse defectResponse = DefectResponse.builder()
                .id(vehicleDefectEntity.getDefect().getId())
                .defectCode(vehicleDefectEntity.getDefect().getDefectCode())
                .defectDesc(vehicleDefectEntity.getDefect().getDefectDesc())
                .build();
        return VehicleDefectResponse.builder()
                .id(vehicleDefectEntity.getId())
                .defectImage(vehicleDefectEntity.getDefectImage())
                .defect(defectResponse)
                .vehicle(VehicleResponse.builder()
                        .id(vehicleDefectEntity.getVehicle().getId())
                        .model(vehicleDefectEntity.getVehicle().getModel())
                        .chassisNumber(vehicleDefectEntity.getVehicle().getChassisNumber())
                        .colour(vehicleDefectEntity.getVehicle().getColour())
                        .build())
                .defectLocation(DefectLocationResponse.builder()
                        .id(vehicleDefectEntity.getDefectLocation().getId())
                        .xLocation(vehicleDefectEntity.getDefectLocation().getXLocation())
                        .yLocation(vehicleDefectEntity.getDefectLocation().getYLocation())
                        .build())
                .build();
    }

    private VehicleDefectEntity mapToEntity(VehicleDefectRequest defectRequest) {
        return VehicleDefectEntity.builder()
                .defectImage(defectRequest.getDefectImage())
                .vehicleDefectDesc(defectRequest.getVehicleDefectDesc())
                .build();
    }

    private DefectLocationEntity mapToDefectEntity(VehicleDefectRequest vehicleDefectRequest) {
        return DefectLocationEntity.builder()
                .xLocation(vehicleDefectRequest.getXLocation())
                .yLocation(vehicleDefectRequest.getYLocation())
                .build();
    }
}
