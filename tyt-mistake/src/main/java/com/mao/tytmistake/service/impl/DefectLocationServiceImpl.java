package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.client.HeaderUtility;
import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.model.entity.DefectLocationEntity;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
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
    public DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest defectLocationRequest) {
        this.isClientValid(headers);

        VehicleDefectEntity vehicleDefectEntity = vehicleDefectService
                .getVehicleDefectEntityById(defectLocationRequest.getDefectId());

        List<DefectLocationEntity> entities = defectLocationRequest.getLocations().stream()
                .map(locationsRequest -> {
                    DefectLocationEntity entity = LocationsRequest.mappedDefectLocationEntity(locationsRequest);
                    entity.setVehicleDefectEntity(vehicleDefectEntity);
                    return entity;
                }).toList();

        List<LocationsResponse> locationsRequests = defectLocationEntityRepository.saveAll(entities).stream()
                .map(LocationsResponse::mappedLocationsResponse).toList();

        return DefectLocationResponse.defectLocationEntityMappedResponse(locationsRequests);
    }

    @Override
    public LocationsResponse updateLocation(HttpHeaders headers, Long id, LocationsRequest locationsRequest) {
        this.isClientValid(headers);

        DefectLocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setXLocation(locationsRequest.getXLocation());
        entity.setYLocation(locationsRequest.getYLocation());
        DefectLocationEntity updatedEntity = defectLocationEntityRepository.save(entity);

        return LocationsResponse.mappedLocationsResponse(updatedEntity);
    }

    //todo: düşünülecek
    @Override
    public List<Long> removeLocation(HttpHeaders headers, LocationRemoveRequest request) {
        this.isClientValid(headers);

        request.getLocationIds().forEach(this::deleteLocations);
        return request.getLocationIds();
    }

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);

        apiClient.validate(clientHeaders, Role.OPERATOR);
    }

    private void deleteLocations(Long id) {
        DefectLocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setIsDeleted(true);
        defectLocationEntityRepository.save(entity);
    }
}
