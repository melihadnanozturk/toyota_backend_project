package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.client.HeaderUtility;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDefectServiceImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;
    private final AuthApiClient apiClient;


    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(HttpHeaders headers, VehicleDefectRequest vehicleDefectRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        defectEntity.setVehicle(vehicleEntity);
        defectEntity.setCreatedBy(user);

        DefectEntity savedDefectEntity = vehicleDefectEntityRepository.save(defectEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(savedDefectEntity);
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(HttpHeaders headers, UpdateVehicleDefectRequest request, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = this.checkVehicleDefectEntityIsExists(id);
        checkImageIsExist(defectEntity, request.getDefectImage());

        defectEntity.setDefectDesc(request.getDefectDesc());
        defectEntity.setDefectImage(request.getDefectImage());
        defectEntity.setUpdatedBy(user);

        DefectEntity updatedEntity = vehicleDefectEntityRepository.save(defectEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(updatedEntity);
    }

    @Override
    public Long deleteVehicleDefect(HttpHeaders headers, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = checkVehicleDefectEntityIsExists(id);

        defectEntity.setIsDeleted(true);
        defectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        defectEntity.setUpdatedBy(user);

        return vehicleDefectEntityRepository.save(defectEntity).getId();
    }

    @Override
    public DefectEntity getVehicleDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    @Override
    public Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
    }

    private DefectEntity checkVehicleDefectEntityIsExists(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkImageIsExist(DefectEntity entity, String image) {
        if (image != null && !entity.getDefectImage().equals(image)) {
            entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        }
    }

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);

        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
