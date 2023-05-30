package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    @Override
    public BaseResponse<VehicleResponse> addNewVehicle(VehicleRequest vehicleRequest) {
        this.checkChassisNumberBeforeInsert(vehicleRequest.getChassisNumber());

        VehicleEntity vehicleEntity = VehicleRequest.requestMappedVehicleEntity(vehicleRequest);
        VehicleEntity savedEntity = vehicleEntityRepository.save(vehicleEntity);
        VehicleResponse response = VehicleResponse.vehicleEntityMappedResponse(savedEntity);

        return BaseResponse.isSuccess(response);
    }

    @Override
    public BaseResponse<VehicleResponse> updateVehicle(Long id, VehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = this.checkVehicleEntityBeforeUpdate(id, vehicleRequest.getChassisNumber());
        VehicleEntity updatedEntity = setVehicle(vehicleEntity, vehicleRequest);
        VehicleEntity savedEntity = vehicleEntityRepository.save(updatedEntity);
        VehicleResponse response = VehicleResponse.vehicleEntityMappedResponse(savedEntity);
        return BaseResponse.isSuccess(response);
    }

    @Override
    public BaseResponse<Long> removeVehicle(Long id) {
        VehicleEntity vehicleEntity = getById(id);
        vehicleEntity.setIsDeleted(true);
        return BaseResponse.isSuccess(id);
    }

    @Override
    public VehicleEntity getById(Long id) {
        return vehicleEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkChassisNumberBeforeInsert(String chassisNumber) {
        if (vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(chassisNumber).isPresent()) {
            throw new AlreadyExistsException(chassisNumber);
        }
    }

    private VehicleEntity checkVehicleEntityBeforeUpdate(Long id, String chassisNumber) {
        VehicleEntity byId = getById(id);
        VehicleEntity byChassisNumber = vehicleEntityRepository
                .findByChassisNumberAndIsDeletedIsFalse(chassisNumber).orElse(null);

        if (byChassisNumber != null && (!byId.getId().equals(byChassisNumber.getId()))) {
            throw new AlreadyExistsException(byChassisNumber.getChassisNumber());
        }

        return byId;
    }

    private VehicleEntity setVehicle(VehicleEntity vehicleEntity, VehicleRequest vehicleRequest) {
        vehicleEntity.setColour(vehicleRequest.getColour());
        vehicleEntity.setChassisNumber(vehicleRequest.getChassisNumber());
        vehicleEntity.setModel(vehicleRequest.getModel());
        return vehicleEntity;
    }

}
