package com.mao.tytmistake.service.impl.service;

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
import com.mao.tytmistake.service.impl.BaseUnitTest;
import com.mao.tytmistake.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

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

        Mockito.when(vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(testChassisNumber))
                .thenReturn(Optional.of(testExistsEntity));

        Assertions.assertThrows(AlreadyExistsException.class, () -> vehicleService.addNewVehicle(testHeaders, testRequest));
        Mockito.verify(apiClient, Mockito.times(1))
                .validate(Mockito.any(HttpHeaders.class), Mockito.any(Role.class));
        Mockito.verify(vehicleEntityRepository, Mockito.times(1))
                .findByChassisNumberAndIsDeletedIsFalse(Mockito.anyString());
    }

    @Test
    void addNewVehicle_happyPath() {
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder().build();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder().build();

        Mockito.when(vehicleEntityRepository.save(Mockito.any(VehicleEntity.class))).thenReturn(testVehicleEntity);

        VehicleResponse response = vehicleService.addNewVehicle(testHeaders, testRequest);

        Assertions.assertEquals(VehicleResponse.vehicleEntityMappedResponse(testVehicleEntity), response);

        Mockito.verify(apiClient, Mockito.times(1))
                .validate(Mockito.any(HttpHeaders.class), Mockito.any(Role.class));
        Mockito.verify(vehicleEntityRepository, Mockito.times(1))
                .save(Mockito.any(VehicleEntity.class));
    }

    @Test
    void updateVehicle_notExistsVehicleId_throwNotFoundException() {
        Long testId = 1L;
        HttpHeaders testHeaders = this.createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder().build();

        Mockito.when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleService.updateVehicle(testHeaders, testId, testRequest));
        Mockito.verify(apiClient, Mockito.times(1))
                .validate(Mockito.any(HttpHeaders.class), Mockito.any(Role.class));
        Mockito.verify(vehicleEntityRepository, Mockito.times(1))
                .findByIdAndIsDeletedIsFalse(Mockito.anyLong());
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

        Mockito.when(vehicleEntityRepository.findByIdAndIsDeletedIsFalse(testId))
                .thenReturn(Optional.of(testVehicleEntityById));
        Mockito.when(vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(testChassisNumber))
                .thenReturn(Optional.of(testVehicleEntityChassisNumber));

        Assertions.assertThrows(AlreadyExistsException.class, () -> vehicleService.updateVehicle(testHeaders, testId, testRequest));
        Mockito.verify(apiClient, Mockito.times(1))
                .validate(Mockito.any(HttpHeaders.class), Mockito.any(Role.class));
        Mockito.verify(vehicleEntityRepository, Mockito.times(1))
                .findByIdAndIsDeletedIsFalse(Mockito.anyLong());
    }

    @Deprecated
    @Test
    void removeVehicle() {
    }

    @Deprecated
    @Test
    void getById() {
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}