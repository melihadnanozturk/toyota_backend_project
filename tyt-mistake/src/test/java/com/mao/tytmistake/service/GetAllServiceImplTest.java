package com.mao.tytmistake.service;

import com.mao.tytmistake.BaseUnitTest;
import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.DefectEntityBuilder;
import com.mao.tytmistake.model.LocationEntityBuilder;
import com.mao.tytmistake.model.VehicleEntityBuilder;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.impl.GetAllServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.mockito.Mockito.*;

class GetAllServiceImplTest extends BaseUnitTest {

    @Mock
    private VehicleEntityRepository vehicleEntityRepository;
    @Mock
    private VehicleDefectEntityRepository vehicleDefectEntityRepository;
    @Mock
    private DefectLocationEntityRepository defectLocationEntityRepository;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    GetAllServiceImpl getAllService;

    @Test
    void getAllVehicle_happyPath() {
        HttpHeaders testHeaders = createHeader();
        PageVehicleRequest testRequest = new PageVehicleRequest();
        VehicleEntity testVehicleEntity = new VehicleEntityBuilder().build();
        List<VehicleEntity> testVehicleEntities = List.of(testVehicleEntity);
        Page<VehicleEntity> testPage = new PageImpl<>(testVehicleEntities);
        PageVehicleResponse expectedResponse = PageVehicleResponse.vehicleEntityMappedPageResponse(testVehicleEntity);

        when(vehicleEntityRepository
                .findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(testPage);

        Page<PageVehicleResponse> response = getAllService.getAllVehicle(testHeaders, testRequest);

        Assertions.assertEquals(response.getContent().size(), testVehicleEntities.size());
        Assertions.assertTrue(response.toList().contains(expectedResponse));
        verify(vehicleEntityRepository, times(1))
                .findAll(any(Specification.class), any(Pageable.class));
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), eq(Role.TEAM_LEAD));
    }

    @Test
    void getAllVehicleDefect_happyPath() {
        HttpHeaders testHeaders = createHeader();
        PageDefectRequest testRequest = new PageDefectRequest();
        DefectEntity testDefectEntity = new DefectEntityBuilder().build();
        Page<DefectEntity> testPage = new PageImpl<>(List.of(testDefectEntity));
        PageDefectResponse testVehicleDefectResponse = PageDefectResponse
                .vehicleDefectEntityMappedPageResponse(testDefectEntity);

        when(vehicleDefectEntityRepository
                .findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(testPage);

        Page<PageDefectResponse> response = getAllService.getAllDefect(testHeaders, testRequest);

        Assertions.assertEquals(1, response.getContent().size());
        Assertions.assertTrue(response.toList().contains(testVehicleDefectResponse));
        verify(vehicleDefectEntityRepository, times(1))
                .findAll(any(Specification.class), any(Pageable.class));
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), eq(Role.TEAM_LEAD));
    }

    @Test
    void getAllLocations_happyPath() {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationEntity testEntity = new LocationEntityBuilder().build();

        when(defectLocationEntityRepository.findAllByVehicleDefectEntityId(testId)).thenReturn(List.of(testEntity));

        List<LocationsResponse> responses = getAllService.getAllLocations(testHeaders, testId);

        Assertions.assertEquals(1, responses.size());
        Assertions.assertTrue(responses.contains(LocationsResponse.mappedLocationsResponse(testEntity)));
        verify(defectLocationEntityRepository, times(1)).findAllByVehicleDefectEntityId(anyLong());
        verify(apiClient, times(1))
                .validate(any(HttpHeaders.class), eq(Role.TEAM_LEAD));
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}