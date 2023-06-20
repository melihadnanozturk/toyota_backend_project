package com.mao.tytmistake.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytmistake.controller.request.*;
import com.mao.tytmistake.controller.response.DefectLocationResponse;
import com.mao.tytmistake.controller.response.DefectLocationResponseBuilder;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.LocationsResponseBuilder;
import com.mao.tytmistake.service.DefectLocationService;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.impl.BaseControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DefectLocationController.class)
class DefectLocationControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/location";

    @MockBean
    public DefectLocationService defectLocationService;
    @MockBean
    public GetAllService getAllService;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllDefectLocation_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationsResponse testResponse = new LocationsResponseBuilder().build();

        Mockito.when(getAllService.getAllLocations(Mockito.any(HttpHeaders.class), Mockito.anyLong()))
                .thenReturn(List.of(testResponse));

        mockMvc.perform(get(COMMON_PATH + "/{defectId}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(getAllService, Mockito.times(1))
                .getAllLocations(Mockito.any(HttpHeaders.class), Mockito.anyLong());
    }

    @Test
    void addNewLocations_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        DefectLocationRequest testRequest = new DefectLocationRequestBuilder().build();
        DefectLocationResponse testResponse = new DefectLocationResponseBuilder().build();

        Mockito.when(defectLocationService.addNewLocation(Mockito.any(HttpHeaders.class), Mockito.any(DefectLocationRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(defectLocationService, Mockito.times(1))
                .addNewLocation(Mockito.any(HttpHeaders.class), Mockito.any(DefectLocationRequest.class));
    }

    @Test
    void updateLocations_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationsRequest testRequest = new LocationsRequestBuilder().build();
        LocationsResponse testResponse = new LocationsResponseBuilder().build();

        Mockito.when(defectLocationService.updateLocation(Mockito.any(HttpHeaders.class), Mockito.anyLong(), Mockito.any(LocationsRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(put(COMMON_PATH + "/{locationId}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(defectLocationService, Mockito.times(1))
                .updateLocation(Mockito.any(HttpHeaders.class), Mockito.anyLong(), Mockito.any(LocationsRequest.class));
    }

    @Test
    void removeLocations_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        LocationRemoveRequest testRequest = new LocationRemoveRequestBuilder().build();

        mockMvc.perform(delete(COMMON_PATH, testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(defectLocationService, Mockito.times(1))
                .removeLocation(Mockito.any(HttpHeaders.class), Mockito.any(LocationRemoveRequest.class));
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}