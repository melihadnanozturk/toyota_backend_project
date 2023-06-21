package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

/**
 * Service implementation for vehicle defects.
 */
@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(DefectServiceImpl.class);


    /**
     * Adds a new defect for a vehicle.
     *
     * @param headers       HttpHeaders containing request headers
     * @param defectRequest DefectRequest specifying defect information
     * @return a DefectResponse representing added defect
     * @throws NotFoundException if vehicle entity not exists
     */
    @SneakyThrows
    @Override
    public DefectResponse addNewDefect(HttpHeaders headers, DefectRequest defectRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = DefectRequest.responseMapToVehicleDefectEntity(defectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(defectRequest.getVehicleId());
        defectEntity.setVehicle(vehicleEntity);
        defectEntity.setCreatedBy(user);

        DefectEntity savedDefectEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with NAME {} has been registered ", defectRequest.getDefect().toString());
        return DefectResponse.vehicleDefectEntityMappedResponse(savedDefectEntity);
    }

    /**
     * Updates an existing defect.
     *
     * @param headers HttpHeaders containing request headers
     * @param request UpdateDefectRequest specifying updated defect information
     * @param id      ID of defect to be updated
     * @return a DefectResponse representing updated defect
     * @throws NotFoundException if defect entity is not found
     */
    @Override
    public DefectResponse updateDefect(HttpHeaders headers, UpdateDefectRequest request, Long id) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = this.checkVehicleDefectEntityIsExists(id);
        checkImageIsExist(defectEntity, request.getDefectImage());

        defectEntity.setDefectDesc(request.getDefectDesc());
        defectEntity.setDefectImage(request.getDefectImage());
        defectEntity.setUpdatedBy(user);

        DefectEntity updatedEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with NAME {} has been updated", defectEntity.getDefect().toString());
        return DefectResponse.vehicleDefectEntityMappedResponse(updatedEntity);
    }

    /**
     * Deletes a defect.
     *
     * @param headers HttpHeaders containing request headers
     * @param id      ID of defect to be deleted
     * @return the ID of deleted defect
     * @throws NotFoundException if defect entity is not found
     */
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

    /**
     * Retrieves DefectEntity by its ID.
     *
     * @param id ID of the DefectEntity to retrieve.
     * @return DefectEntity with the specified ID.
     * @throws NotFoundException if DefectEntity is not found.
     */
    @Override
    public DefectEntity getDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    /**
     * Sets "isDeleted" flag to true for given DefectEntity and all its associated DefectLocation objects.
     *
     * @param entity DefectEntity to be marked as deleted.
     */
    private void mappedSoftDelete(DefectEntity entity) {
        entity.setIsDeleted(true);
        entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
    }

    /**
     * Checks if DefectEntity with given ID exists.
     *
     * @param id ID of DefectEntity to check.
     * @return DefectEntity with specified ID.
     * @throws NotFoundException if DefectEntity is not found.
     */
    private DefectEntity checkVehicleDefectEntityIsExists(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    /**
     * Checks if specified image exists for given DefectEntity. If image is different from one stored
     * in entity, marks all associated DefectLocation objects as deleted.
     *
     * @param entity DefectEntity to check for image.
     * @param image  image to compare with one stored in entity.
     */
    private void checkImageIsExist(DefectEntity entity, String image) {
        if (image != null && !entity.getDefectImage().equals(image)) {
            entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        }
    }

    /**
     * Validates client by checking provided headers and user information.
     *
     * @param headers HttpHeaders containing client information.
     */
    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
