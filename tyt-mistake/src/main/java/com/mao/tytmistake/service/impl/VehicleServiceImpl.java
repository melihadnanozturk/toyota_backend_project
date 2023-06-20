package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(VehicleServiceImpl.class);

    @Override
    public VehicleResponse addNewVehicle(HttpHeaders headers, VehicleRequest vehicleRequest) {
        this.isClientValid(headers);
        this.checkChassisNumberBeforeInsert(vehicleRequest.getChassisNumber());
        String user = HeaderUtility.getUser(headers);

        VehicleEntity vehicleEntity = VehicleRequest.requestMappedVehicleEntity(vehicleRequest);
        vehicleEntity.setCreatedBy(user);

        VehicleEntity savedEntity = vehicleEntityRepository.save(vehicleEntity);
        logger.atInfo().log("Entity with ID {} has been registered ", savedEntity.getId());

        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public VehicleResponse updateVehicle(HttpHeaders headers, Long id, VehicleRequest vehicleRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        VehicleEntity vehicleEntity = this.checkVehicleEntityBeforeUpdate(id, vehicleRequest.getChassisNumber());
        VehicleEntity updatedEntity = setVehicle(vehicleEntity, vehicleRequest);
        vehicleEntity.setUpdatedBy(user);

        VehicleEntity savedEntity = vehicleEntityRepository.save(updatedEntity);
        logger.atInfo().log("Entity with ID {} has been updated ", id);

        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public Long removeVehicle(HttpHeaders headers, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        VehicleEntity vehicleEntity = getById(id);

        this.mappedSoftDelete(vehicleEntity);
        vehicleEntity.setUpdatedBy(user);

        vehicleEntityRepository.save(vehicleEntity);
        logger.atInfo().log("Entity with ID {} has been removed ", id);
        return id;
    }

    @Override
    public VehicleEntity getById(Long id) {
        return vehicleEntityRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkChassisNumberBeforeInsert(String chassisNumber) {
        if (vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(chassisNumber).isPresent()) {
            throw new AlreadyExistsException(chassisNumber);
        }
    }

    private VehicleEntity checkVehicleEntityBeforeUpdate(Long id, String chassisNumber) {
        VehicleEntity byId = this.getById(id);
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

    private void mappedSoftDelete(VehicleEntity vehicleEntity) {
        vehicleEntity.setIsDeleted(true);
        vehicleEntity.getDefect().forEach(defectEntity -> {
            defectEntity.setIsDeleted(true);
            defectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        });
    }

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
