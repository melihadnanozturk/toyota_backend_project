package com.mao.tytmistake.service;

import com.mao.tytmistake.BaseUnitTest;
import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.VehicleRequestBuilder;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.VehicleEntityBuilder;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.Mockito.*;


class VehicleServiceImplTest extends BaseUnitTest {

    @Mock
    private VehicleEntityRepository vehicleEntityRepository;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @Test
    void addNewVehicle_existsChassisNumber_throwAlreadyExistsException() {
        String testChassisNumber = "existsChassisNumber";
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder()
                .withChassisNumber(testChassisNumber)
                .build();
        VehicleEntity testExistsEntity = new VehicleEntityBuilder()
                .withChassisNumber(testChassisNumber)
                .build();

        when(vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(testChassisNumber))
                .thenReturn(Optional.of(testExistsEntity));

        Assertions.assertThrows(AlreadyExistsException.class, () -> vehicleService.addNewVehicle(testHeaders, testRequest));
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), any(Role.class));
        verify(vehicleEntityRepository, times(1))
                .findByChassisNumberAndIsDeletedIsFalse(anyString());
    }

    @Test
    void addNewVehicle_happyPath() {
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder().build();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder().build();

        when(vehicleEntityRepository.save(any(VehicleEntity.class))).thenReturn(testVehicleEntity);

        VehicleResponse response = vehicleService.addNewVehicle(testHeaders, testRequest);

        Assertions.assertEquals(VehicleResponse.vehicleEntityMappedResponse(testVehicleEntity), response);

        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), any(Role.class));
        verify(vehicleEntityRepository, times(1))
                .save(any(VehicleEntity.class));
    }

    @Test
    void updateVehicle_notExistsVehicleId_throwNotFoundException() {
        Long testId = 1L;
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder().build();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.updateVehicle(testHeaders, testId, testRequest));
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), any(Role.class));
        verify(vehicleEntityRepository, times(1))
                .findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void updateVehicle_existsChassisNumberAndIdIsNotEquals_throwAlreadyExistsException() {
        Long testId = 1L;
        String testChassisNumber = "existChassisNumber";
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder()
                .withChassisNumber(testChassisNumber)
                .build();
        VehicleEntity testVehicleEntityById = new VehicleEntityBuilder()
                .withId(57L)
                .withChassisNumber(testChassisNumber)
                .build();
        VehicleEntity testVehicleEntityChassisNumber = new VehicleEntityBuilder()
                .withId(61L)
                .withChassisNumber(testChassisNumber)
                .build();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId))
                .thenReturn(Optional.of(testVehicleEntityById));
        when(vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(testChassisNumber))
                .thenReturn(Optional.of(testVehicleEntityChassisNumber));

        Assertions.assertThrows(AlreadyExistsException.class, () -> vehicleService.updateVehicle(testHeaders, testId, testRequest));
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), any(Role.class));
        verify(vehicleEntityRepository, times(1))
                .findByIdAndIsDeletedIsFalse(anyLong());
    }

    @Test
    void updateVehicle_happyPath() {
        Long testId = 1L;
        String testChassisNumber = "testChassisNumber";
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder()
                .withChassisNumber(testChassisNumber)
                .build();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder()
                .withId(57L)
                .withChassisNumber(testChassisNumber)
                .build();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId))
                .thenReturn(Optional.of(testVehicleEntity));
        when(vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(testChassisNumber))
                .thenReturn(Optional.of(testVehicleEntity));
        when(vehicleEntityRepository.save(any(VehicleEntity.class)))
                .thenReturn(testVehicleEntity);

        VehicleResponse response = vehicleService.updateVehicle(testHeaders, testId, testRequest);

        Assertions.assertEquals(VehicleResponse.vehicleEntityMappedResponse(testVehicleEntity), response);
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), any(Role.class));
        verify(vehicleEntityRepository, times(1))
                .findByIdAndIsDeletedIsFalse(anyLong());
        verify(vehicleEntityRepository, times(1))
                .findByChassisNumberAndIsDeletedIsFalse(anyString());
        verify(vehicleEntityRepository, times(1))
                .save(any(VehicleEntity.class));
    }

    @Deprecated
    @Test
    void removeVehicle_notExistsVehicleId_throwNotFoundException() {
        Long testId = 57L;
        HttpHeaders testHeaders = this.createHeader();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.removeVehicle(testHeaders, testId));

        verify(vehicleEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
    }

    @Test
    void removeVehicle_happyPath() {
        Long testId = 57L;
        HttpHeaders testHeaders = this.createHeader();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder()
                .withId(testId)
                .build();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId))
                .thenReturn(Optional.of(testVehicleEntity));

        Long response = vehicleService.removeVehicle(testHeaders, testId);

        Assertions.assertEquals(testId, response);
        verify(vehicleEntityRepository, times(1)).findByIdAndIsDeletedIsFalse(anyLong());
        verify(apiClient, times(1)).validate(any(HttpHeaders.class), any(Role.class));
    }

    @Test
    void getById_notExistsVehicleId_throwNotFoundException() {
        Long testId = 57L;

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.getById(testId));
    }

    @Test
    void getById_happyPath() {
        Long testId = 57L;
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder().build();

        when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.of(testVehicleEntity));

        VehicleEntity response = vehicleService.getById(testId);
        Assertions.assertEquals(testVehicleEntity, response);
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}