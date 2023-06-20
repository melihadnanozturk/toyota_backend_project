package com.mao.tytmistake.service.impl.service;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.*;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.model.DefectEntityBuilder;
import com.mao.tytmistake.model.LocationEntityBuilder;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import com.mao.tytmistake.service.impl.BaseUnitTest;
import com.mao.tytmistake.service.impl.DefectLocationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

class DefectLocationServiceImplTest extends BaseUnitTest {

    @Mock
    private DefectLocationEntityRepository defectLocationEntityRepository;
    @Mock
    private VehicleDefectService vehicleDefectService;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    private DefectLocationServiceImpl defectLocationService;

    @Test
    void addNewLocation_happyPath() {
        Long testDefectId = 57L;
        HttpHeaders testHeaders = createHeader();
        DefectLocationRequest testRequest = new DefectLocationRequestBuilder()
                .withDefectId(testDefectId).build();
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectId).build();
        LocationEntity testLocationEntity = new LocationEntityBuilder().build();

        Mockito.when(vehicleDefectService.getVehicleDefectEntityById(testDefectId)).thenReturn(testDefectEntity);
        Mockito.when(defectLocationEntityRepository.saveAll(Mockito.any(List.class))).thenReturn(List.of(testLocationEntity));

        DefectLocationResponse response = defectLocationService.addNewLocation(testHeaders, testRequest);

        Assertions.assertTrue(response.getLocationsResponseList().contains(LocationsResponse.mappedLocationsResponse(testLocationEntity)));
        Mockito.verify(vehicleDefectService, Mockito.times(1)).getVehicleDefectEntityById(Mockito.anyLong());
        Mockito.verify(defectLocationEntityRepository, Mockito.times(1)).saveAll(Mockito.anyList());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateLocation_notExistsLocationId_throwNotFoundException() {
        Long testLocationId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationsRequest testRequest = new LocationsRequestBuilder().build();

        Mockito.when(defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(testLocationId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> defectLocationService.updateLocation(testHeaders, testLocationId, testRequest));
        Mockito.verify(defectLocationEntityRepository, Mockito.times(1)).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateLocation_happyPath() {
        Long testLocationId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationsRequest testRequest = new LocationsRequestBuilder().build();
        LocationEntity testLocationEntity = new LocationEntityBuilder()
                .withId(testLocationId)
                .build();

        LocationsResponse expected = LocationsResponse.mappedLocationsResponse(testLocationEntity);

        Mockito.when(defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(testLocationId)).thenReturn(Optional.of(testLocationEntity));
        Mockito.when(defectLocationEntityRepository.save(testLocationEntity)).thenReturn(testLocationEntity);

        LocationsResponse response = defectLocationService.updateLocation(testHeaders, testLocationId, testRequest);

        Assertions.assertEquals(expected, response);
        Mockito.verify(defectLocationEntityRepository, Mockito.times(1)).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void removeLocation_notExistsLocationId_throwNotFoundException() {
        Long testLocationId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationRemoveRequest testRequest = new LocationRemoveRequestBuilder()
                .withIds(List.of(testLocationId))
                .build();

        Mockito.when(defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(testLocationId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> defectLocationService.removeLocation(testHeaders, testRequest));
        Mockito.verify(defectLocationEntityRepository, Mockito.times(testRequest.getLocationIds().size())).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void removeLocation_happyPath() {
        Long testLocationId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationRemoveRequest testRequest = new LocationRemoveRequestBuilder()
                .withIds(List.of(testLocationId))
                .build();
        LocationEntity testLocationEntity = new LocationEntityBuilder()
                .withId(testLocationId)
                .build();

        Mockito.when(defectLocationEntityRepository.findByIdAndIsDeletedIsFalse(testLocationId)).thenReturn(Optional.of(testLocationEntity));

        List<Long> response = defectLocationService.removeLocation(testHeaders, testRequest);

        Assertions.assertEquals(testRequest.getLocationIds().size(), response.size());
        Assertions.assertTrue(response.contains(testLocationId));
        Mockito.verify(defectLocationEntityRepository, Mockito.times(testRequest.getLocationIds().size())).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
        Mockito.verify(defectLocationEntityRepository, Mockito.times(testRequest.getLocationIds().size())).save(Mockito.any(LocationEntity.class));
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}