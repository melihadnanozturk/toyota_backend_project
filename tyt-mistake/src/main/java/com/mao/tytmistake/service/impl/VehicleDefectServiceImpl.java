package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDefectServiceImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(VehicleDefectServiceImpl.class);


    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewDefect(HttpHeaders headers, VehicleDefectRequest vehicleDefectRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        defectEntity.setVehicle(vehicleEntity);
        defectEntity.setCreatedBy(user);

        DefectEntity savedDefectEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with NAME {} has been registered ", vehicleDefectRequest.getDefect().toString());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(savedDefectEntity);
    }

    @Override
    public VehicleDefectResponse updateDefect(HttpHeaders headers, UpdateVehicleDefectRequest request, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = this.checkVehicleDefectEntityIsExists(id);
        checkImageIsExist(defectEntity, request.getDefectImage());

        defectEntity.setDefectDesc(request.getDefectDesc());
        defectEntity.setDefectImage(request.getDefectImage());
        defectEntity.setUpdatedBy(user);

        DefectEntity updatedEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with NAME {} has been updated", defectEntity.getDefect().toString());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(updatedEntity);
    }

    @Override
    public Long deleteDefect(HttpHeaders headers, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = checkVehicleDefectEntityIsExists(id);
        this.mappedSoftDelete(defectEntity);

        defectEntity.setUpdatedBy(user);

        logger.atInfo().log("Defect with NAME {} has been removed", defectEntity.getDefect().toString());
        return vehicleDefectEntityRepository.save(defectEntity).getId();
    }

    private void mappedSoftDelete(DefectEntity entity) {
        entity.setIsDeleted(true);
        entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
    }

    @Override
    public DefectEntity getDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
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
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
