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

/**
 * Service implementation for managing vehicles.
 */
@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(VehicleServiceImpl.class);

    /**
     * Adds a new vehicle based on provided information.
     *
     * @param headers        HttpHeaders containing client information.
     * @param vehicleRequest VehicleRequest object containing vehicle details.
     * @return VehicleResponse object representing newly added vehicle.
     * @throws AlreadyExistsException if a vehicle with same chassis number already exists.
     */
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

    /**
     * Updates information of an existing vehicle.
     *
     * @param headers        HttpHeaders containing client information.
     * @param id             ID of vehicle to update.
     * @param vehicleRequest VehicleRequest object containing updated vehicle details.
     * @return VehicleResponse object representing updated vehicle.
     * @throws NotFoundException      if vehicle is not found.
     * @throws AlreadyExistsException if a vehicle with same updated chassis number already exists.
     */
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

    /**
     * Removes a vehicle based on provided ID.
     *
     * @param headers HttpHeaders containing client information.
     * @param id      ID of the vehicle to remove.
     * @return ID of the removed vehicle.
     * @throws NotFoundException if vehicle is not found.
     */
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

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id ID of vehicle to retrieve.
     * @return VehicleEntity with specified ID.
     * @throws NotFoundException if vehicle is not found.
     */
    @Override
    public VehicleEntity getById(Long id) {
        return vehicleEntityRepository.findByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    /**
     * Checks if a vehicle with given chassis number already exists before inserting a new vehicle.
     *
     * @param chassisNumber Chassis number of vehicle to check.
     * @throws AlreadyExistsException if a vehicle with same chassis number already exists.
     */
    private void checkChassisNumberBeforeInsert(String chassisNumber) {
        if (vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(chassisNumber).isPresent()) {
            throw new AlreadyExistsException(chassisNumber);
        }
    }

    /**
     * Checks if a vehicle with given ID and chassis number already exists before updating a vehicle.
     *
     * @param id            ID of the vehicle to update.
     * @param chassisNumber Updated chassis number of vehicle to check.
     * @return VehicleEntity with specified ID.
     * @throws NotFoundException      if vehicle with the given ID is not found.
     * @throws AlreadyExistsException if a vehicle with the same updated chassis number already exists.
     */
    private VehicleEntity checkVehicleEntityBeforeUpdate(Long id, String chassisNumber) {
        VehicleEntity byId = this.getById(id);
        VehicleEntity byChassisNumber = vehicleEntityRepository
                .findByChassisNumberAndIsDeletedIsFalse(chassisNumber).orElse(null);

        if (byChassisNumber != null && (!byId.getId().equals(byChassisNumber.getId()))) {
            throw new AlreadyExistsException(byChassisNumber.getChassisNumber());
        }

        return byId;
    }

    /**
     * Updates attributes of a vehicle entity based on the provided vehicle request.
     *
     * @param vehicleEntity  Original VehicleEntity object to update.
     * @param vehicleRequest VehicleRequest object containing updated vehicle details.
     * @return Updated VehicleEntity object.
     */
    private VehicleEntity setVehicle(VehicleEntity vehicleEntity, VehicleRequest vehicleRequest) {
        vehicleEntity.setColour(vehicleRequest.getColour());
        vehicleEntity.setChassisNumber(vehicleRequest.getChassisNumber());
        vehicleEntity.setModel(vehicleRequest.getModel());
        return vehicleEntity;
    }

    /**
     * Performs a soft delete by setting the "isDeleted" flag to true for a vehicle and its associated defects and defect locations.
     *
     * @param vehicleEntity VehicleEntity to be deleted.
     */
    private void mappedSoftDelete(VehicleEntity vehicleEntity) {
        vehicleEntity.setIsDeleted(true);
        vehicleEntity.getDefect().forEach(defectEntity -> {
            defectEntity.setIsDeleted(true);
            defectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        });
    }

    /**
     * Validates client by checking provided headers and user information.
     *
     * @param headers HttpHeaders containing the client information.
     */
    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

}
