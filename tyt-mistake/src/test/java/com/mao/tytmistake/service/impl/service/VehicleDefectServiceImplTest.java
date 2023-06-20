package com.mao.tytmistake.service.impl.service;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.DefectEntityBuilder;
import com.mao.tytmistake.model.VehicleEntityBuilder;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.impl.BaseUnitTest;
import com.mao.tytmistake.service.impl.VehicleDefectServiceImpl;
import com.mao.tytmistake.service.impl.VehicleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;

class VehicleDefectServiceImplTest extends BaseUnitTest {

    @Mock
    private VehicleDefectEntityRepository vehicleDefectEntityRepository;
    @Mock
    private VehicleServiceImpl vehicleService;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    private VehicleDefectServiceImpl vehicleDefectService;


    @Test
    void addNewDefect_happyPath() {
        HttpHeaders testHeaders = createHeader();
        Long testVehicleId = 61L;
        VehicleDefectRequest tesVehicleDefectRequest = new VehicleDefectRequestBuilder()
                .witVehicleId(testVehicleId)
                .build();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder()
                .withId(testVehicleId)
                .build();
        DefectEntity testDefectEntity = new DefectEntityBuilder().build();

        Mockito.when(vehicleService.getById(testVehicleId)).thenReturn(testVehicleEntity);
        Mockito.when(vehicleDefectEntityRepository.save(Mockito.any(DefectEntity.class))).thenReturn(testDefectEntity);

        VehicleDefectResponse response = vehicleDefectService.addNewVehicleDefect(testHeaders, tesVehicleDefectRequest);

        Assertions.assertEquals(VehicleDefectResponse.vehicleDefectEntityMappedResponse(testDefectEntity), response);
        Mockito.verify(vehicleService, Mockito.times(1)).getById(Mockito.anyLong());
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).save(Mockito.any(DefectEntity.class));
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateDefect_notExistsDefectId_throwNotFoundException() {
        Long testVehicleDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();
        UpdateVehicleDefectRequest tesRequest = new UpdateVehicleDefectRequestBuilder().build();

        Mockito.when(vehicleDefectEntityRepository.findById(testVehicleDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.updateVehicleDefect(testHeaders, tesRequest, testVehicleDefectEntityId));
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void updateDefect_happyPath() {
        Long testDefectEntityId = 9L;
        String testImage = "testImage";
        HttpHeaders testHeaders = createHeader();
        UpdateVehicleDefectRequest testRequest = new UpdateVehicleDefectRequestBuilder()
                .withImage("image")
                .build();
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId)
                .withImage(testImage)
                .build();

        Mockito.when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));
        Mockito.when(vehicleDefectEntityRepository.save(Mockito.any(DefectEntity.class))).thenReturn(testDefectEntity);

        VehicleDefectResponse response = vehicleDefectService.updateVehicleDefect(testHeaders, testRequest, testDefectEntityId);

        Assertions.assertEquals(VehicleDefectResponse.vehicleDefectEntityMappedResponse(testDefectEntity), response);
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).save(Mockito.any(DefectEntity.class));
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void deleteDefect_notExistsDefectId_throwNotFoundException() {
        Long testDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();

        Mockito.when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.deleteVehicleDefect(testHeaders, testDefectEntityId));
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void deleteDefect_happyPath() {
        Long testDefectEntityId = 9L;
        HttpHeaders testHeaders = createHeader();
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId).build();

        Mockito.when(vehicleDefectEntityRepository.findById(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));
        Mockito.when(vehicleDefectEntityRepository.save(Mockito.any(DefectEntity.class))).thenReturn(testDefectEntity);

        Long response = vehicleDefectService.deleteVehicleDefect(testHeaders, testDefectEntityId);

        Assertions.assertEquals(testDefectEntityId, response);
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).save(Mockito.any(DefectEntity.class));
        Mockito.verify(apiClient, Mockito.times(1)).validate(Mockito.any(HttpHeaders.class), eq(Role.OPERATOR));
    }

    @Test
    void getDefectEntityById_notExistsDefectId_throwNotFoundException() {
        Long testDefectEntityId = 9L;

        Mockito.when(vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(testDefectEntityId)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> vehicleDefectService.getVehicleDefectEntityById(testDefectEntityId));
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
    }

    @Test
    void getDefectEntityById_happyPath() {
        Long testDefectEntityId = 9L;
        DefectEntity testDefectEntity = new DefectEntityBuilder().withId(testDefectEntityId).build();

        Mockito.when(vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(testDefectEntityId)).thenReturn(Optional.of(testDefectEntity));

        DefectEntity response = vehicleDefectService.getVehicleDefectEntityById(testDefectEntityId);

        Assertions.assertEquals(testDefectEntity, response);
        Mockito.verify(vehicleDefectEntityRepository, Mockito.times(1)).findByIdAndIsDeletedIsFalse(Mockito.anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}