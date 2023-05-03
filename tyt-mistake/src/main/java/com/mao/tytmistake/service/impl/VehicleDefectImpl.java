package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleDefectImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleService vehicleService;


    @Override
    public PageVehicleDefectResponse getAllVehicleDefect() {
        List<VehicleDefectResponse> vehicleDefectResponses = vehicleDefectEntityRepository.findAll()
                .stream().map(VehicleDefectResponse::vehicleDefectEntityMappedResponse).toList();

        return PageVehicleDefectResponse.builder()
                .defectEntities(vehicleDefectResponses)
                .build();
    }

    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest vehicleDefectRequest) {
        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());

        VehicleDefectEntity vehicleDefectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);
        vehicleDefectEntity.setVehicle(vehicleEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(UpdateVehicleDefectRequest vehicleDefectRequest) {
        /*VehicleDefectEntity vehicleDefectEntity = checkExist(vehicleDefectRequest.getId());
        vehicleDefectEntity.setDefectImage(vehicleDefectRequest.getVehicleDefectRequest().getDefectImage());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));*/
        return null;
    }

    @Override
    public VehicleDefectEntity getVehicleDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    //todo: kontrol et
    @Override
    public Long deleteVehicleDefect(Long id) {
        VehicleDefectEntity vehicleDefectEntity = checkVehicleDefectEntityIsExists(id);

        vehicleDefectEntity.setIsDeleted(true);
        vehicleDefectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));

        return vehicleDefectEntityRepository.save(vehicleDefectEntity).getId();
    }

    private VehicleDefectEntity checkVehicleDefectEntityIsExists(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }
}
