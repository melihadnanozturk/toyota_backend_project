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
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectLocationServiceImpl implements DefectLocationService {

    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final VehicleDefectService vehicleDefectService;
    private final AuthApiClient apiClient;

    @Override
    public DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest request) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        DefectEntity defectEntity = vehicleDefectService
                .getVehicleDefectEntityById(request.getDefectId());

        List<LocationEntity> entities = this.mappedLocationRequestToLocationEntity(request, user, defectEntity);

        List<LocationsResponse> locationsRequests = defectLocationEntityRepository.saveAll(entities).stream()
                .map(LocationsResponse::mappedLocationsResponse).toList();

        return DefectLocationResponse.defectLocationEntityMappedResponse(locationsRequests);
    }

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

        return LocationsResponse.mappedLocationsResponse(updatedEntity);
    }

    @Override
    public List<Long> removeLocation(HttpHeaders headers, LocationRemoveRequest request) {
        this.isClientValid(headers);
        String user = HeaderUtility.getUser(headers);

        request.getLocationIds().forEach(id -> this.deleteLocations(id, user));
        return request.getLocationIds();
    }

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

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);

        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

    private void deleteLocations(Long id, String user) {
        LocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setIsDeleted(true);
        entity.setUpdatedBy(user);

        defectLocationEntityRepository.save(entity);
    }
}
