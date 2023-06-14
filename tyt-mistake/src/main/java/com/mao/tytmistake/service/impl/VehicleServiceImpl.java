package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final AuthApiClient apiClient;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";


    @Override
    public VehicleResponse addNewVehicle(HttpHeaders headers, VehicleRequest vehicleRequest) {
        this.isValidRequest(headers);
        this.checkChassisNumberBeforeInsert(vehicleRequest.getChassisNumber());

        VehicleEntity vehicleEntity = VehicleRequest.requestMappedVehicleEntity(vehicleRequest);
        VehicleEntity savedEntity = vehicleEntityRepository.save(vehicleEntity);
        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public VehicleResponse updateVehicle(HttpHeaders headers, Long id, VehicleRequest vehicleRequest) {
        this.isValidRequest(headers);

        VehicleEntity vehicleEntity = this.checkVehicleEntityBeforeUpdate(id, vehicleRequest.getChassisNumber());
        VehicleEntity updatedEntity = setVehicle(vehicleEntity, vehicleRequest);
        VehicleEntity savedEntity = vehicleEntityRepository.save(updatedEntity);
        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public Long removeVehicle(HttpHeaders headers, Long id) {
        this.isValidRequest(headers);

        VehicleEntity vehicleEntity = getById(id);
        vehicleEntity.setIsDeleted(true);
        return id;
    }

    @Override
    public VehicleEntity getById(Long id) {
        return vehicleEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void isValidRequest(HttpHeaders headers) {
        Map<String, String> info = getHeaderInfo(headers);

        apiClient.validate(info.get(USER_NAME), info.get(TOKEN), Role.OPERATOR);
    }

    private Map<String, String> getHeaderInfo(HttpHeaders headers) {
        Map<String, String> infos = new HashMap<>();

        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        infos.put(USER_NAME, userName);
        infos.put(TOKEN, token);

        return infos;
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
