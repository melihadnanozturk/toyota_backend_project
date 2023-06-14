package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class DefectLocationServiceImpl implements DefectLocationService {

    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final VehicleDefectService vehicleDefectService;
    private final AuthApiClient apiClient;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public DefectLocationResponse addNewLocation(HttpHeaders headers, DefectLocationRequest defectLocationRequest) {
        this.isValidRequest(headers);

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
        this.isValidRequest(headers);

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
        this.isValidRequest(headers);

        request.getLocationIds().forEach(this::deleteLocations);
        return request.getLocationIds();
    }

    private void deleteLocations(Long id) {
        DefectLocationEntity entity = defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        entity.setIsDeleted(true);
        defectLocationEntityRepository.save(entity);
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
}
