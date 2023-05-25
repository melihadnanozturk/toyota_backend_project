package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDefectServiceImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;

    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest vehicleDefectRequest) {
        VehicleDefectEntity vehicleDefectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        vehicleDefectEntity.setVehicle(vehicleEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(UpdateVehicleDefectRequest request, Long id) {
        VehicleDefectEntity vehicleDefectEntity = this.checkVehicleDefectEntityIsExists(id);
        vehicleDefectEntity.setVehicleDefectDesc(request.getVehicleDefectDesc());
        vehicleDefectEntity.setDefectImage(request.getDefectImage());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectEntity getVehicleDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    @Override
    public Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
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
