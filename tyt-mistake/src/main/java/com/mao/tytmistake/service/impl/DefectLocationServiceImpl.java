package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.DefectLocationRequest;
import com.mao.tytmistake.controller.request.LocationRemoveRequest;
import com.mao.tytmistake.controller.request.LocationsRequest;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.model.entity.DefectLocationEntity;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.service.DefectLocationService;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectLocationServiceImpl implements DefectLocationService {

    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final VehicleDefectService vehicleDefectService;

    @Override
    public DefectLocationResponse addNewLocation(DefectLocationRequest defectLocationRequest) {
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
    public LocationsResponse updateLocation(Long id, LocationsRequest locationsRequest) {
        DefectLocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setXLocation(locationsRequest.getXLocation());
        entity.setYLocation(locationsRequest.getYLocation());
        DefectLocationEntity updatedEntity = defectLocationEntityRepository.save(entity);

        return LocationsResponse.mappedLocationsResponse(updatedEntity);
    }

    //todo: düşünülecek
    @Override
    public List<Long> removeLocation(LocationRemoveRequest request) {
        request.getLocationIds().forEach(this::deleteLocations);
        return request.getLocationIds();
    }

    private void deleteLocations(Long id) {
        DefectLocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setIsDeleted(true);
        defectLocationEntityRepository.save(entity);
    }
}
