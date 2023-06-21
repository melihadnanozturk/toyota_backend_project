package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.service.DefectLocationService;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the DefectLocationService interface that provides functionality for managing defect locations.
 */
@Service
@RequiredArgsConstructor
public class DefectLocationServiceImpl implements DefectLocationService {

    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final DefectService defectService;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(DefectLocationServiceImpl.class);


    /**
     * This method create new location when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains new location information
     * @throws NotFoundException if defect is not exists
     */
    @Override
    public DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest request) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = defectService
                .getDefectEntityById(request.getDefectId());

        List<LocationEntity> entities = this.mappedLocationRequestToLocationEntity(request, user, defectEntity);

        List<LocationsResponse> locationsRequests = defectLocationEntityRepository.saveAll(entities).stream()
                .map(LocationsResponse::mappedLocationsResponse).toList();

        logger.atInfo().log("Locations with DEFECT_ID {} has been registered", request.getDefectId());
        return DefectLocationResponse.defectLocationEntityMappedResponse(locationsRequests);
    }

    /**
     * This method update exists location when client is valid.
     *
     * @param headers          Username, Password
     * @param locationsRequest Contains location new information
     * @throws NotFoundException if location that has id not exists
     */
    @Override
    public LocationsResponse updateLocation(HttpHeaders headers, Long id, LocationsRequest locationsRequest) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        LocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setXLocation(locationsRequest.getXLocation());
        entity.setYLocation(locationsRequest.getYLocation());
        entity.setUpdatedBy(user);

        LocationEntity updatedEntity = defectLocationEntityRepository.save(entity);

        logger.atInfo().log("Locations with ID {} has been updated", id);
        return LocationsResponse.mappedLocationsResponse(updatedEntity);
    }

    /**
     * This method remove exists location when client is valid.
     *
     * @param headers Username, Password
     * @param request Contains id of the location to be deleted
     * @throws NotFoundException if location that has id not exists
     */
    @Override
    public List<Long> removeLocation(HttpHeaders headers, LocationRemoveRequest request) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        request.getLocationIds().forEach(id -> this.deleteLocations(id, user));
        return request.getLocationIds();
    }

    /**
     * Maps the defect location request to a list of location entities.
     *
     * @param request      Defect location request
     * @param user         User who created the locations
     * @param defectEntity Defect entity associated with the locations
     * @return List of location entities
     */
    private List<LocationEntity> mappedLocationRequestToLocationEntity(DefectLocationRequest request,
                                                                       String user,
                                                                       DefectEntity defectEntity) {
        return request.getLocations().stream()
                .map(locationsRequest -> {
                    LocationEntity entity = LocationsRequest.mappedDefectLocationEntity(locationsRequest);
                    entity.setDefectEntity(defectEntity);
                    entity.setCreatedBy(user);
                    return entity;
                }).toList();
    }

    /**
     * Deletes the defect locations with the specified ID.
     *
     * @param id   ID of the defect location to be deleted
     * @param user User who performs the deletion
     * @throws NotFoundException if the defect location with specified ID is not found
     */
    private void deleteLocations(Long id, String user) {
        LocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setIsDeleted(true);
        entity.setUpdatedBy(user);

        defectLocationEntityRepository.save(entity);
        logger.atInfo().log("Locations with ID {} has been removed", id);
    }

    /**
     * Validates the client's authorization and authentication.
     *
     * @param headers HTTP headers containing the necessary information for validation
     */
    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.OPERATOR);
    }
}
