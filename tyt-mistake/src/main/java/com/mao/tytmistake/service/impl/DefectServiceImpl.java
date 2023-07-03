package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

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

    @Override
    public DefectResponse addNewDefect(HttpHeaders headers, DefectRequest defectRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = DefectRequest.responseMapToVehicleDefectEntity(defectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(defectRequest.getVehicleId());
        defectEntity.setVehicle(vehicleEntity);
        defectEntity.setCreatedBy(user);

        DefectEntity savedDefectEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with Name {} has been registered ", defectRequest.getDefect().toString());
        return DefectResponse.vehicleDefectEntityMappedResponse(savedDefectEntity);
    }

    /**
     * Adds an image to a defect.
     *
     * @param headers   HTTP headers containing client information.
     * @param imageFile Image file to be added to the defect.
     * @param id        ID of the defect.
     * @return ID of the defect after adding the image.
     * @throws AlreadyExistsException if a defect image already exists for the given defect.
     * @throws NotFoundException      if the defect with the given ID is not found.
     */
    @Override
    @SneakyThrows
    public Long addDefectImage(HttpHeaders headers, MultipartFile imageFile, Long id) {
        this.isClientValid(headers);
        DefectEntity entity = this.getDefectEntityById(id);
        if (entity.getDefectImage() != null) {
            throw new AlreadyExistsException("defect image");
        }

        byte[] imageData = imageFile.getBytes();
        entity.setDefectImage(imageData);

        logger.atInfo().log("Defect image with Id {} has been registered", id);
        return vehicleDefectEntityRepository.save(entity).getId();
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

        DefectEntity defectEntity = this.getDefectEntityById(id);
        defectEntity.setDefectDesc(request.getDefectDesc());
        defectEntity.setUpdatedBy(user);

        DefectEntity updatedEntity = vehicleDefectEntityRepository.save(defectEntity);

        logger.atInfo().log("Defect with Name {} has been updated", defectEntity.getDefect().toString());
        return DefectResponse.vehicleDefectEntityMappedResponse(updatedEntity);
    }

    /**
     * Updates the image of a defect.
     *
     * @param headers   HTTP headers containing client information.
     * @param imageFile New image file to be updated for defect.
     * @param defectId  ID of defect.
     * @return Updated ID of  defect.
     * @throws NotFoundException if the defect with given ID is not found.
     */
    @SneakyThrows
    @Override
    public Long updateDefectImage(HttpHeaders headers, MultipartFile imageFile, Long defectId) {
        this.isClientValid(headers);
        byte[] imageData = imageFile.getBytes();
        DefectEntity defectEntity = this.getDefectEntityById(defectId);
        return checkImageIsExist(defectEntity, imageData).getId();
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

        DefectEntity defectEntity = getDefectEntityById(id);
        this.mappedSoftDelete(defectEntity);

        defectEntity.setUpdatedBy(user);

        logger.atInfo().log("Defect with Name {} has been removed", defectEntity.getDefect().toString());
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
     * Checks if specified image exists for given DefectEntity. If image is different from one stored
     * in entity, marks all associated DefectLocation objects as deleted.
     *
     * @param entity    DefectEntity to check for image.
     * @param imageData image to compare with one stored in entity.
     */
    private DefectEntity checkImageIsExist(DefectEntity entity, byte[] imageData) {
        if (entity.getDefectImage() == null) {
            throw new NotFoundException("images " + entity.getId());
        }
        if (!Arrays.equals(entity.getDefectImage(), imageData)) {
            entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
            entity.setDefectImage(imageData);

            logger.atInfo().log("Defect image with Defect Id {} has been updated", entity.getId());
            return vehicleDefectEntityRepository.save(entity);
        }
        throw new AlreadyExistsException("same defect image");
    }

    /**
     * Validates client by checking provided headers and user information.
     *
     * @param headers HttpHeaders containing client information.
     */
    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with Name {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
