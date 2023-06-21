package com.mao.tytmistake.service;

import com.mao.tytmistake.BaseUnitTest;
import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.VehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.DefectEntityBuilder;
import com.mao.tytmistake.model.VehicleEntityBuilder;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.impl.DefectServiceImpl;
import com.mao.tytmistake.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


class DefectServiceImplTest extends BaseUnitTest {

    @Mock
    private VehicleDefectEntityRepository vehicleDefectEntityRepository;
    @Mock
    private VehicleServiceImpl vehicleService;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    private DefectServiceImpl vehicleDefectService;


    @Test
    void addNewDefect_happyPath() {
        HttpHeaders testHeaders = createHeader();
        Long testVehicleId = 61L;
        DefectRequest tesDefectRequest = new VehicleDefectRequestBuilder()
                .witVehicleId(testVehicleId)
                .build();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder()
                .withId(testVehicleId)
                .build();
        DefectEntity testDefectEntity = new DefectEntityBuilder().build();

        when(vehicleService.getById(testVehicleId)).thenReturn(testVehicleEntity);
        when(vehicleDefectEntityRepository.save(any(DefectEntity.class))).thenReturn(testDefectEntity);

        DefectResponse response = vehicleDefectService.addNewDefect(testHeaders, tesDefectRequest);

        Assertions.assertEquals(DefectResponse.vehicleDefectEntityMappedResponse(testDefectEntity), response);
        verify(vehicleService, times(1)).getById(anyLong());
        verify(vehicleDefectEntityRepository, times(1)).save(any(DefectEntity.class));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateDefect_notExistsDefectId_throwNotFoundException() {
        Long testVehicleDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();
        UpdateDefectRequest tesRequest = new UpdateVehicleDefectRequestBuilder().build();

        when(vehicleDefectEntityRepository.findById(testVehicleDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.updateDefect(testHeaders, tesRequest, testVehicleDefectEntityId));
        verify(vehicleDefectEntityRepository, times(1)).findById(anyLong());
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateDefect_happyPath() {
        Long testDefectEntityId = 9L;
        String testImage = "testImage";
        HttpHeaders testHeaders = createHeader();
        UpdateDefectRequest testRequest = new UpdateVehicleDefectRequestBuilder()
                .withImage("image")
                .build();
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId)
                .withImage(testImage)
                .build();

        when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));
        when(vehicleDefectEntityRepository.save(any(DefectEntity.class))).thenReturn(testDefectEntity);

        DefectResponse response = vehicleDefectService.updateDefect(testHeaders, testRequest, testDefectEntityId);

        Assertions.assertEquals(DefectResponse.vehicleDefectEntityMappedResponse(testDefectEntity), response);
        verify(vehicleDefectEntityRepository, times(1)).findById(anyLong());
        verify(vehicleDefectEntityRepository, times(1)).save(any(DefectEntity.class));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void deleteDefect_notExistsDefectId_throwNotFoundException() {
        Long testDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();

        when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.deleteDefect(testHeaders, testDefectEntityId));
        verify(vehicleDefectEntityRepository, times(1)).findById(anyLong());
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void deleteDefect_happyPath() {
        Long testDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId).build();

        when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));
        when(vehicleDefectEntityRepository.save(any(DefectEntity.class))).thenReturn(testDefectEntity);

        Long response = vehicleDefectService.deleteDefect(testHeaders, testDefectEntityId);

        Assertions.assertEquals(testDefectEntityId, response);
        verify(vehicleDefectEntityRepository, times(1)).findById(anyLong());
        verify(vehicleDefectEntityRepository, times(1)).save(any(DefectEntity.class));
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void getDefectEntityById_notExistsDefectId_throwNotFoundException() {
        Long testDefectEntityId = 9L;

        when(vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(testDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.getDefectEntityById(testDefectEntityId));
        verify(vehicleDefectEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void getDefectEntityById_happyPath() {
        Long testDefectEntityId = 9L;
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId).build();

        when(vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));

        DefectEntity response = vehicleDefectService.getDefectEntityById(testDefectEntityId);

        Assertions.assertEquals(testDefectEntity, response);
        verify(vehicleDefectEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}